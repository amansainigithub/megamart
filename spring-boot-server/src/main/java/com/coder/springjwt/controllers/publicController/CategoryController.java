package com.coder.springjwt.controllers.publicController;


import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL+"/categoryController")
public class CategoryController {
    @Autowired
    private PublicService publicService;

    @GetMapping("/getProductCategory")
    public ResponseEntity<?> getProductCategory() {
        return this.publicService.getProductCategoryService();
    }



}
