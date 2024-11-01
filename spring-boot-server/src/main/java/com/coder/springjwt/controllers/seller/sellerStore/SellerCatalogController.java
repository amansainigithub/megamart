package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerCatalogService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_CATALOG_CONTROLLER)
public class SellerCatalogController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService simpleEmailService;

    @Autowired
    SellerCatalogService sellerCatalogService;

    @GetMapping(SellerUrlMappings.SELLER_GET_CATALOG)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellerCatalog(@PathVariable Long catalogId) {
        return sellerCatalogService.getSellerCatalog(catalogId);
    }


    @GetMapping(SellerUrlMappings.GET_GST_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getGstList(@PathVariable Long catalogId) {
        return sellerCatalogService.getGstList(catalogId);
    }

    @GetMapping(SellerUrlMappings.GET_CATALOG_MASTERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getCatalogMasters() {
        return sellerCatalogService.getCatalogMasters();
    }

    @PostMapping("/uploadMultiFiles/{categoryId}/{index}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> uploadMultiFiles(@RequestParam("file") MultipartFile file,
                                              @PathVariable Long categoryId ,
                                              @PathVariable String index) {


        System.out.println("Category Id  :: " + categoryId);
        System.out.println("Index :: " + index);
        System.out.println("Data :: " + file.getOriginalFilename());
        return ResponseEntity.ok("message");
    }


    @PostMapping(SellerUrlMappings.SELLER_SAVE_CATALOG)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> sellerSaveCatalog(@Validated @RequestBody SellerCatalogPayload sellerCatalogPayload) {

        return sellerCatalogService.sellerSaveCatalogService(sellerCatalogPayload);
    }




}
