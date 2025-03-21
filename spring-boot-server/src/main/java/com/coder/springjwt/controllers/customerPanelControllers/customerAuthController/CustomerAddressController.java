package com.coder.springjwt.controllers.customerPanelControllers.customerAuthController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPanelDtos.addressDto.AddressDto;
import com.coder.springjwt.services.publicService.addressService.AddressService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(CustomerUrlMappings.ADDRESS_CONTROLLER)
public class CustomerAddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(CustomerUrlMappings.SAVE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> saveAddress(@Valid @RequestBody AddressDto addressDto) {
        return this.addressService.saveAddress(addressDto);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddress() {
        return this.addressService.getAddress();
    }

    @GetMapping(CustomerUrlMappings.DELETE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAddress(@PathVariable(required = true) long id) {
        return this.addressService.deleteAddress(id);
    }


    @GetMapping(CustomerUrlMappings.SET_DEFAULT_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> setDefaultAddress(@PathVariable(required = true) long id) {
        return this.addressService.setDefaultAddress(id);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS_BY_ID)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddressById(@PathVariable(required = true) long id) {
        return this.addressService.getAddressById(id);
    }

    @PostMapping(CustomerUrlMappings.UPDATE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressDto addressDto) {
        return this.addressService.updateAddress(addressDto);
    }



    @GetMapping("/generate-qr")
    public ResponseEntity<Map<String, String>> generateQRCode(
            @RequestParam String vpa,
            @RequestParam String name,
            @RequestParam double amount,
            @RequestParam String transactionNote
    ) {
        try {
            System.out.println("QR code API Fly");
            String qrCodeData = "upi://pay?pa=" + vpa +
                    "&pn=" + name +
                    "&am=" + amount +
                    "&cu=INR" +
                    "&tn=" + transactionNote;
//
//            byte[] qrCodeImage = qrCodeGenerator.generateQRCodeImageBytes(qrCodeData, 250, 250);
//            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
//
//            // Create response JSON
//            Map<String, String> response = new HashMap<>();
//            response.put("qrCode", "data:image/png;base64," + qrCodeBase64);
//            response.put("amount", String.valueOf(amount));
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.IMAGE_PNG);
////            return ResponseEntity.ok().headers(headers).body(qrCodeImage);
//            return ResponseEntity.ok().body(response);

            byte[] qrCodeImage = generateQRCodeImageBytes(qrCodeData, 250, 250);
            String qrCodeBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(qrCodeImage);

            Map<String, String> response = new HashMap<>();
            response.put("qrCode", qrCodeBase64);
            response.put("amount", String.valueOf(amount));

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    public void generateQRCodeImage(String qrCodeData, int width, int height, String filePath) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public byte[] generateQRCodeImageBytes(String qrCodeData, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }









}
