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





}
