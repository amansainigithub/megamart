package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerCatalogPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerCatalogService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerStoreService;
import com.coder.springjwt.util.ResponseGenerator;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


    @PostMapping("/uploadMultiFiles/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> uploadMultiFiles(@PathVariable Long categoryId ,
                                              @RequestPart("catalogData") SellerCatalogPayload sellerCatalogPayload,
                                              @RequestParam(value = "files" , required = false) List<MultipartFile> files) {
        System.out.println(sellerCatalogPayload);
        return sellerCatalogService.sellerSaveCatalogService(categoryId , sellerCatalogPayload , files );

    }

    @GetMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_USERNAME)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByUsername() {
        return sellerCatalogService.getAllCatalogByUsernameService();

    }

    @GetMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_QC_PROGRESS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByQcProgress() {
        return sellerCatalogService.getAllCatalogByQcProgressService();
    }

    @GetMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_DRAFT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByDraft() {
        return sellerCatalogService.getAllCatalogByDraft();

    }






}
