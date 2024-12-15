package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootData;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.HsnRepository;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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


    @GetMapping("/formBuilderFlying/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> formBuilderFlying(@PathVariable String categoryId){
        return this.sellerProductService.formBuilderFlying(categoryId);
    }

    ProductRootData productRootData = new ProductRootData();
    @GetMapping("/getRows")
    @PreAuthorize("hasRole('SELLER')")
    public ProductRootData getRows() {
        return this.productRootData;
    }

    @PostMapping("/saveRows")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveRows(@RequestBody ProductRootData productRootData) {
        // Save data (you can replace this with database interaction)
        this.productRootData = productRootData;
        System.out.println(this.productRootData);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @PostMapping("/saveSellerProduct")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveSellerProduct(@RequestBody ProductRootData productRootData) {
        this.productRootData = productRootData;
        return sellerProductService.saveSellerProduct(productRootData);
    }

//    @PostMapping("/uploadFile")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<String> uploadFile(@RequestParam("files") List<MultipartFile> files
//                                             ) {
//        try {
//
//            if (files.isEmpty()) {
//                return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
//            }
////            String originalFilename = files.getOriginalFilename();
//            System.out.println("Original File Names :: " + files.size());
//
//            return new ResponseEntity<>("File uploaded successfully: " + files.size(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/uploadProductFiles/{productLockerNumber}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> uploadProductFiles(@RequestParam Map<String, MultipartFile> files , @PathVariable String productLockerNumber) {
         return sellerProductService.uploadProductFiles(files, productLockerNumber);
    }


    @GetMapping("/getImageFile")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getImageFile() {
        Map<String,String> map =new HashMap<>();
        map.put("fileName","https://images.unsplash.com/photo-1720048171731-15b3d9d5473f?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        System.out.println(map);
        return ResponseEntity.ok(map);
    }
}
