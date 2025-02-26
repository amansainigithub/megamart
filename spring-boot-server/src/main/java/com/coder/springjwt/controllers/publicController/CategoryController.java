package com.coder.springjwt.controllers.publicController;


import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL+"/categoryController")
public class CategoryController {
    @Autowired
    private PublicService publicService;

    @GetMapping("/getProductCategory")
    public ResponseEntity<?> getProductCategory() {
        return this.publicService.getProductCategoryService();
    }

    @GetMapping("/getProductListByCategoryId/{categoryId}")
    public ResponseEntity<?> getProductListByCategoryId(@PathVariable long categoryId,
                                                        @RequestParam Integer page ,
                                                        @RequestParam  Integer size) {
        return this.publicService.getProductListByCategoryId(categoryId,page,size);
    }





}
