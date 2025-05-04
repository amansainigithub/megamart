package com.coder.springjwt.services.publicService.invoiceDownloadService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.invoiceDto.InvoiceDto;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.addressRepository.AddressRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.returnExchangeRepository.ReturnExchangeRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.publicService.invoiceDownloadService.InvoiceDownloadService;
import com.coder.springjwt.util.ResponseGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
public class InvoiceDownloadServiceImple implements InvoiceDownloadService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ReturnExchangeRepository returnExchangeRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void invoiceDownload(long id , HttpServletResponse response) {
        log.info("invoiceDownload flying....");
        log.info("Order Id :: " + id);
        try {

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

            //Order Item
            CustomerOrderItems orderItems = this.orderItemsRepository.findOrderItemsById(user.getId(), id);

            //Customer Address
            CustomerAddress address = this.addressRepository.findById(Long.parseLong(orderItems.getAddressId()))
                                            .orElseThrow(() -> new RuntimeException(CustMessageResponse.ADDRESS_NOT_FOUND));

            //Product Data for TAX and PRICE
            SellerProduct sellerProduct = this.sellerProductRepository.findById(Long.parseLong(orderItems.getProductId()))
                                        .orElseThrow(() -> new RuntimeException(CustMessageResponse.ADDRESS_NOT_FOUND));


            //Creating Invoice DTO
            InvoiceDto invoiceDto = new InvoiceDto();

            //Business Name
            invoiceDto.setBusinessAddress("Chilkana Road Near Shiv Mendir, Saharanpur 247001 (U.P)");

            //Customer Address
            invoiceDto.setCustomerName(address.getCustomerName());
            invoiceDto.setArea(address.getArea());
            invoiceDto.setAddressLine1(address.getAddressLine1());
            invoiceDto.setAddressLine2(address.getAddressLine2());
            invoiceDto.setPostalCode(address.getPostalCode());
            invoiceDto.setCountry(address.getCountry());
            invoiceDto.setMobileNumber(address.getMobileNumber());


            invoiceDto.setPaymentMode(orderItems.getPaymentMode());
            invoiceDto.setInvoiceNumber(orderItems.getInvoiceNumber());
            invoiceDto.setInvoiceDate(orderItems.getInvoiceDateTime());
            invoiceDto.setOrderDate(orderItems.getOrderDateTime());
            invoiceDto.setOrderNumber(orderItems.getCustomOrderNumber());


            invoiceDto.setProductName(orderItems.getProductName());
            invoiceDto.setHsn(sellerProduct.getHsn());
            invoiceDto.setGrossAmount(orderItems.getProductPrice());
            invoiceDto.setQuantity(orderItems.getQuantity());
            invoiceDto.setProductSize(orderItems.getProductSize());
            invoiceDto.setTax(sellerProduct.getGst());

            //calculating Price with GST
            double amount = Double.parseDouble(orderItems.getProductPrice());
            double gst = Double.parseDouble(sellerProduct.getGst().replace("%",""));
            double gstAmount = (gst / 100) * amount;
            invoiceDto.setGstAmount(gstAmount);

            invoiceDto.setTotalPrice(amount);

            // Step 2: Set up Thymeleaf context
            Context context = new Context();
            context.setVariable("invoice", invoiceDto);

            //Logo Convert to Base 64 and set to context
            File file = ResourceUtils.getFile("classpath:static/images/pepsi.png");
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
            context.setVariable("logoBase64", base64Logo);

            // Step 3: Render HTML from template
            String html = templateEngine.process("invoice", context);

            // Step 4: Set response headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");


            // Render PDF
            try (OutputStream os = response.getOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(html);
                renderer.layout();
                renderer.createPDF(os, false);
                renderer.finishPDF();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
