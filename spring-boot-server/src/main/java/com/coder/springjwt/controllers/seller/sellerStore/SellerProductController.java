package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormProductVariantBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

//    @GetMapping(SellerUrlMappings.SELLER_GET_CATALOG)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getSellerCatalog(@PathVariable Long catalogId) {
//        return sellerProductService.getSellerCatalog(catalogId);
//    }


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


//    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_USERNAME)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getAllCatalogByUsername(@RequestParam Integer page , @RequestParam  Integer size) {
//        return sellerProductService.getAllCatalogByUsernameService(page,size);
//
//    }
//
//    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_QC_PROGRESS)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getAllCatalogByQcProgress(@RequestParam Integer page , @RequestParam  Integer size) {
//        return sellerProductService.getAllCatalogByQcProgressService(page,size);
//    }
//
//    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_DRAFT)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getAllCatalogByDraft(@RequestParam Integer page , @RequestParam  Integer size) {
//        return sellerProductService.getAllCatalogByDraft(page,size);
//
//    }
//
//
//    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_ERROR)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getAllCatalogByError(@RequestParam Integer page , @RequestParam  Integer size) {
//        return sellerProductService.getAllCatalogByError(page,size);
//
//    }
//
//    @PostMapping(SellerUrlMappings.GET_ALL_CATALOG_BY_QC_PASS)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<?> getAllCatalogByQcPass(@RequestParam Integer page , @RequestParam  Integer size) {
//        return sellerProductService.getAllCatalogByQcPass(page,size);
//    }


    @GetMapping("/formBuilderFlying/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> formBuilderFlying(@PathVariable String categoryId){
        return this.sellerProductService.formBuilderFlying(categoryId);
    }


    @PostMapping("/saveSellerProductNew/{bornCategoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveSellerProductNew(@RequestBody ProductRootBuilder productRootBuilder , @PathVariable Long bornCategoryId) {
        return sellerProductService.saveSellerProductNew(productRootBuilder , bornCategoryId);
    }

    @PostMapping("/uploadProductFiles/{productLockerNumber}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> uploadProductFiles(@RequestParam Map<String, MultipartFile> files , @PathVariable String productLockerNumber) {
        return sellerProductService.uploadProductFiles(files, productLockerNumber);
    }


    @GetMapping("/getProductById/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        return sellerProductService.getProductBYId(productId);
    }

    @GetMapping("/getAllIncompleteProduct")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllIncompleteProduct() {
        return sellerProductService.getAllIncompleteProduct();
    }




}
