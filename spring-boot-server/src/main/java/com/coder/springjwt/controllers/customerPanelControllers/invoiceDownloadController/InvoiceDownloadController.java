package com.coder.springjwt.controllers.customerPanelControllers.invoiceDownloadController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.invoiceDownloadService.InvoiceDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.INVOICE_DOWNLOAD_CONTROLLER)
public class InvoiceDownloadController {

    @Autowired
    private InvoiceDownloadService invoiceDownloadService;

    @GetMapping(value = CustomerUrlMappings.INVOICE_DOWNLOAD , produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void invoiceDownload(@PathVariable long id , HttpServletResponse response) {
         this.invoiceDownloadService.invoiceDownload(id , response);
    }

}
