package com.coder.springjwt.services.publicService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface PublicService {

    ResponseEntity<?> getProductCategoryService();
}
