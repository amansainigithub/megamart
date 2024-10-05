package com.coder.springjwt.controllers.seller.sellarTax;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerTaxPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.sellerServices.sellerTaxService.SellerTaxService;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_TAX_CONTROLLER)
public class SellerTaxController {


    @Autowired
    private SellerTaxService sellerTaxService;

    @PostMapping(SellerUrlMappings.SELLER_TAX)
    public ResponseEntity<?> sellerTax(@Validated @RequestBody SellerTaxPayload sellerTaxPayload) {

        return sellerTaxService.saveAndVerifyTaxDetails(sellerTaxPayload);
    }



}
