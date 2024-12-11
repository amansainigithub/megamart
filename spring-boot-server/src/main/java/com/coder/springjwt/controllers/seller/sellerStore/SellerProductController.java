package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerProductPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.HsnRepository;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PRODUCT_CONTROLLER)
public class SellerProductController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BornCategoryRepo bornCategoryRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService simpleEmailService;

    @Autowired
    SellerProductService sellerProductService;

    @GetMapping(SellerUrlMappings.SELLER_GET_CATALOG)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getSellerCatalog(@PathVariable Long catalogId) {
        return sellerProductService.getSellerCatalog(catalogId);
    }


    @GetMapping(SellerUrlMappings.GET_GST_LIST)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getGstList(@PathVariable Long catalogId) {
        return sellerProductService.getGstList(catalogId);
    }

    @GetMapping(SellerUrlMappings.GET_PRODUCT_MASTERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductMasters() {
        return sellerProductService.getProductMasters();
    }


    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_USERNAME)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByUsername(@RequestParam Integer page , @RequestParam  Integer size) {
        return sellerProductService.getAllCatalogByUsernameService(page,size);

    }

    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_QC_PROGRESS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByQcProgress(@RequestParam Integer page , @RequestParam  Integer size) {
        return sellerProductService.getAllCatalogByQcProgressService(page,size);
    }

    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_DRAFT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByDraft(@RequestParam Integer page , @RequestParam  Integer size) {
        return sellerProductService.getAllCatalogByDraft(page,size);

    }


    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_ERROR)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByError(@RequestParam Integer page , @RequestParam  Integer size) {
        return sellerProductService.getAllCatalogByError(page,size);

    }

    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_QC_PASS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllCatalogByQcPass(@RequestParam Integer page , @RequestParam  Integer size) {
        return sellerProductService.getAllCatalogByQcPass(page,size);
    }


    @Autowired
    private HsnRepository hsnRepository;
    @GetMapping("/getProductDataFormBuilder/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> productDataFormBuilder(@PathVariable String categoryId){
        return this.sellerProductService.productDataFormBuilder(categoryId);
    }

    ProductRootData productRootData = new ProductRootData();
    @PostMapping("/saveRows")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveRows(@RequestBody ProductRootData productRootData) {
        // Save data (you can replace this with database interaction)
        this.productRootData = productRootData;
        System.out.println(this.productRootData);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/getRows")
    @PreAuthorize("hasRole('SELLER')")
    public ProductRootData getRows() {
        return this.productRootData;
    }


}
