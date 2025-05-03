package com.coder.springjwt.services.publicService.invoiceDownloadService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface InvoiceDownloadService {
    void invoiceDownload(long id, HttpServletResponse response);
}
