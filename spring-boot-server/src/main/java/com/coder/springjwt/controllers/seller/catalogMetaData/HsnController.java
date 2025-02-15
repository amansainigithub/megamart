package com.coder.springjwt.controllers.seller.catalogMetaData;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerDtos.hsn.HsnCodesDto;
import com.coder.springjwt.exception.services.sellerServices.hsnService.HsnCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_HSN_CONTROLLER)
public class HsnController {


    @Autowired
    private HsnCodeService hsnCodeService;

    @PostMapping(SellerUrlMappings.SAVE_HSN)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveHsn(@Validated @RequestBody HsnCodesDto hsnCodesDto) {
        return this.hsnCodeService.saveHsn(hsnCodesDto);
    }

    @GetMapping(SellerUrlMappings.DELETE_HSN)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> deleteHsnCode(@PathVariable long hsnCodeId ) {
        return this.hsnCodeService.deleteHsnCode(hsnCodeId);
    }

    @GetMapping(SellerUrlMappings.GET_HSN_CODE_BY_ID)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHsnCodeById(@PathVariable long hsnCodeId ) {
        return this.hsnCodeService.getHsnCodeById(hsnCodeId);
    }

    @PostMapping(SellerUrlMappings.UPDATE_HSN_CODE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateHsnCode(@Validated @RequestBody HsnCodesDto hsnCodesDto ) {
        return this.hsnCodeService.updateHsnCode(hsnCodesDto);
    }

    @PostMapping(SellerUrlMappings.GET_HSN_LIST_BY_PAGINATION)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getHsnListByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.hsnCodeService.getHsnCodesPagination(page,size);
    }


}
