package com.coder.springjwt.controllers.customerPanelControllers.categoryController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPanelDtos.filterDto.ProductFilterDto;
import com.coder.springjwt.services.publicService.productService.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(CustomerUrlMappings.CATEGORY_CONTROLLER)
public class CategoryController {
    @Autowired
    private PublicService publicService;

    @GetMapping(CustomerUrlMappings.GET_PRODUCT_CATEGORY)
    public ResponseEntity<?> getProductCategory() {
        return this.publicService.getProductCategoryService();
    }

    @GetMapping(CustomerUrlMappings.GET_PRODUCT_LIST_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductListByCategoryId(@RequestParam long cI,
                                                        @RequestParam String cN,
                                                        @RequestParam Integer page ,
                                                        @RequestParam  Integer size) {
        return this.publicService.getProductListByCategoryId(cI , cN , page , size);
    }

    @GetMapping(CustomerUrlMappings.GET_PRODUCT_LIST_BY_BORN_CATEGORY_ID)
    public ResponseEntity<?> getProductListByBornCategoryId(@RequestParam long cI,
                                                            @RequestParam String cN,
                                                            @RequestParam Integer page ,
                                                            @RequestParam  Integer size) {
        return this.publicService.getProductListByBornCategoryId(cI , cN , page , size);
    }

    @GetMapping(CustomerUrlMappings.PRODUCT_WATCHING)
    public ResponseEntity<?> productWatching(
                                             @RequestParam String pI ,
                                             @RequestParam String pN) {
        return this.publicService.productWatching(pI, pN);
    }

    @GetMapping(CustomerUrlMappings.PRODUCT_SEARCHING)
    public ResponseEntity<?> productSearching( @RequestParam String searchKey) {
        return this.publicService.productSearching(searchKey);
    }

    @PostMapping(CustomerUrlMappings.PRODUCT_FILTER)
    public ResponseEntity<?> productFilter(@RequestBody ProductFilterDto productFilterDto,
                                           @RequestParam Integer page ,
                                           @RequestParam  Integer size) {
        return this.publicService.productFilter(productFilterDto,page,size);
    }




}
