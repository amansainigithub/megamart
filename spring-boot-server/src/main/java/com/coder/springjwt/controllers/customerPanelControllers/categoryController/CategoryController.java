package com.coder.springjwt.controllers.customerPanelControllers.categoryController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.productService.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/getProductListByCategoryId")
    public ResponseEntity<?> getProductListByCategoryId(@RequestParam long cI,
                                                        @RequestParam String cN,
                                                        @RequestParam Integer page ,
                                                        @RequestParam  Integer size) {
        return this.publicService.getProductListByCategoryId(cI , cN , page , size);
    }

    @GetMapping("/getProductListByBornCategoryId")
    public ResponseEntity<?> getProductListByBornCategoryId(@RequestParam long cI,
                                                            @RequestParam String cN,
                                                            @RequestParam Integer page ,
                                                            @RequestParam  Integer size) {
        return this.publicService.getProductListByBornCategoryId(cI , cN , page , size);
    }

    @GetMapping("/getProductListDeal99")
    public ResponseEntity<?> getProductListDeal99(@RequestParam long cI,
                                                  @RequestParam String cN,
                                                  @RequestParam Integer page ,
                                                  @RequestParam  Integer size) {
        return this.publicService.getProductListDeal99(cI , cN , page , size);
    }


    @GetMapping("/productWatching")
    public ResponseEntity<?> productWatching(
                                             @RequestParam String pI ,
                                             @RequestParam String pN) {
        return this.publicService.productWatching(pI, pN);
    }


}
