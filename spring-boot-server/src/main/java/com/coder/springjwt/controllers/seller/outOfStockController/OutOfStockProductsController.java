package com.coder.springjwt.controllers.seller.outOfStockController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.outOfStockService.OutOfStockProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.OUT_OF_STOCK_PRODUCTS_CONTROLLER)
public class OutOfStockProductsController {

    @Autowired
    private OutOfStockProductsService outOfStockProductsService;


    @GetMapping(SellerUrlMappings.GET_OUT_OF_STOCK_PRODUCTS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getOutOfStocks(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.outOfStockProductsService.getOutOfStockProductsService(page,size);
    }

    @PostMapping(SellerUrlMappings.UPDATE_OUT_OF_STOCK_PRODUCTS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateOutOfStocksProducts(@PathVariable long id  , @PathVariable  String productInventory) {
        return this.outOfStockProductsService.updateOutOfStocksProducts(id,productInventory);
    }
}
