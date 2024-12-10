package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.payload.sellerPayloads.sellerPayload.SellerProductPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.catalogRepos.HsnRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
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




    @PostMapping("/productFly/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> productFly(@PathVariable Long categoryId,
                                        @RequestBody SellerProductPayload sellerProductPayload
                                        ) {



        return sellerProductService.productFlyService(categoryId , sellerProductPayload  );

    }


//    // This would be replaced by your service layer
//    private List<ProductData> productDataList = new ArrayList<>(); // fetch from database;
//




    // Get all product data
//    @GetMapping("/getDynamicForm")
//    public Root getProductData() {
//        // Mocking data
//        List<ProductData> productDataList = new ArrayList<>();
//
//        ProductData product1 = new ProductData();
//        product1.setId(1L);
//        product1.setIdentifier("prod_001");
//        product1.setName("Product A");
//        product1.setType("dropdown");
//        product1.setMandatory(true);
//        product1.setDescription("This is product A");
//        product1.setMin(2);
//        product1.setMax(2);
//        product1.setValues(List.of("Option 1", "Option 2", "Option 3"));
//        product1.setValueUnit("units");
//        product1.setAllowCopy(true);
//
//        ProductData product2 = new ProductData();
//        product2.setId(2L);
//        product2.setIdentifier("prod_002");
//        product2.setName("Product B");
//        product2.setType("text");
//        product2.setMandatory(true);
//        product2.setDescription("This is product B");
//        product2.setMin(2);
//        product2.setMax(100);
//        product2.setValues(null);
//        product2.setValueUnit("units");
//        product2.setAllowCopy(false);
//
//        productDataList.add(product1);
//        productDataList.add(product2);
//
//        Root root = new Root();
//        root.setProductData(productDataList);
//        return root;
//    }
//    @GetMapping("/manufacturer-details")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<FormConfig> getFormConfig() {
//        // Example configuration (this could be dynamic based on your business logic)
//        FormField nameField = new FormField("name", "Manufacturer Name", "text", true, "Enter the manufacturer name", null);
//        FormField addressField = new FormField("address", "Manufacturer Address", "text", true, "Enter the address of the manufacturer" ,null);
//
//        // Adding size dropdown
//        FormField sizeField = new FormField("size", "Size", "dropdown", true, "Select the size", List.of("Small", "Medium", "Large"));
//        // Adding colorVariant as multiple selection (checkbox group)
//        FormField colorVariant = new FormField("colorVariant", "Color Variant", "checkbox", true, "Select color variants",  List.of("Red", "Blue", "Green", "Black"));
//
//        FormConfig formConfig = new FormConfig();
//        formConfig.setFields(List.of(nameField, addressField, sizeField,colorVariant));
//
//        return ResponseEntity.ok(formConfig);
//    }
//
//    private final Map<Long, ProductData> productDataStore = new HashMap<>(); // In-memory storage
//
//    // Save new product
//    @PostMapping("/saveDyn")
//    public ProductData saveProduct(@RequestBody ProductData productData) {
//        if (productDataStore.containsKey(productData.getId())) {
//            throw new IllegalArgumentException("Product with ID " + productData.getId() + " already exists!");
//        }
//        productDataStore.put(productData.getId(), productData);
//        return productData;
//    }
//
//    // Update existing product
//    @PutMapping("/update")
//    public ProductData updateProduct(@RequestBody ProductData productData) {
//        if (!productDataStore.containsKey(productData.getId())) {
//            throw new IllegalArgumentException("Product with ID " + productData.getId() + " does not exist!");
//        }
//        productDataStore.put(productData.getId(), productData); // Replace existing product
//        return productData;
//    }
//
//
//    @PostMapping("/setDummy")
//    @PreAuthorize("hasRole('SELLER')")
//    public List<ProductData> setDummyData(@RequestBody HashMap<String,String> map) {
//        ProductData dummy1 = new ProductData(3L, "prod_003", "Dummy Product 1", "text", true,
//                "Dummy description 1", 10, 50, null, "kg", true);
//        ProductData dummy2 = new ProductData(4L, "prod_004", "Dummy Product 2", "dropdown", false,
//                "Dummy description 2", null, null, List.of("Choice 1", "Choice 2"), "liters", false);
//
//        productDataStore.put(dummy1.getId(), dummy1);
//        productDataStore.put(dummy2.getId(), dummy2);
//
//        return new ArrayList<>(productDataStore.values());
//    }
//
//
//
//    ArrayList<ManufacturerDetails> arrayList =new ArrayList<>();
//    @PostMapping("/save")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ManufacturerDetails> save(@RequestBody ManufacturerDetails manufacturerDetails) {
//        this.arrayList.add(manufacturerDetails);
//        System.out.println(arrayList.toString());
//        return ResponseEntity.ok(manufacturerDetails);
//    }
//
//    @GetMapping("/get/{id}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ManufacturerDetails> getById(@PathVariable Long id) {
//        ManufacturerDetails manufacturerDetails = new ManufacturerDetails(id,"samsung","Jai Ho", "Large",null,"1234");
//        return manufacturerDetails != null ? ResponseEntity.ok(manufacturerDetails) : ResponseEntity.notFound().build();
//    }

    @GetMapping("/formConfig")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<Map<String, Object>>> getFormConfig() {
        List<Map<String, Object>> formConfig = new ArrayList<>();

        // Example field configurations
        formConfig.add(Map.of(
                "identifier", "name",
                "type", "text",
                "name", "Name",
                "description", "Enter your name",
                "required", true
        ));

        formConfig.add(Map.of(
                "identifier", "age",
                "type", "text",
                "name", "Age",
                "description", "Enter your age",
                "required", true,
                "min", 18,
                "max", 100
        ));

        formConfig.add(Map.of(
                "identifier", "gender",
                "type", "dropdown",
                "name", "Gender",
                "values", List.of("Male", "Female", "Other"),
                "required", true
        ));

        formConfig.add(Map.of(
                "identifier", "hobbies",
                "type", "multi-select",
                "name", "Hobbies",
                "values", List.of("Reading", "Traveling", "Sports", "Music"),
                "required", false
        ));

        return ResponseEntity.ok(formConfig);
    }


    private Map<String, Object> savedData = new HashMap<>();
    @PostMapping("/submitForm")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> submitForm(@RequestBody Map<String, Object> formData) {
        // Save the submitted form data
        savedData = formData;
        System.out.println("Form Data: " + formData);
        return ResponseEntity.ok("Form submitted successfully");
    }

    @GetMapping("/getSavedData")
    public ResponseEntity<Map<String, Object>> getSavedData() {
        // Return the saved data
        System.out.println("Load Data " + savedData);
        return ResponseEntity.ok(savedData);
    }

//        FormField nameField = new FormField("name", "Manufacturer Name", "text", true, "Enter the manufacturer name", null);

    @Autowired
    private HsnRepository hsnRepository;
    @GetMapping("/getProductDataFormBuilder/{categoryId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> productDataFormBuilder(@PathVariable String categoryId){

        FormBuilderRoot  formBuilderRoot = new FormBuilderRoot();

        List<HsnCodes> hsnCodes = hsnRepository.findAll();
        if(categoryId.equals("1"))
        {
            SizeDataBuilder sizeField = new SizeDataBuilder();
            sizeField.setIdentifier("productSize");
            sizeField.setName("productSize");
            sizeField.setType("multi-select");
            sizeField.setRequired(true);
            sizeField.setDescription("Size");
            sizeField.setMin("");
            sizeField.setMax("");
            sizeField.setValues(List.of("Free Size" , "S" , "M" , "L" , "XXL" ,"3XL","SM","2M", "SSL", "PXL","5XLS"));
            List<SizeDataBuilder> sizeDataBuilderList = new ArrayList<>();
            sizeDataBuilderList.add(sizeField);



            //Table Data
            TableDataBuilder variantSize = new TableDataBuilder();
            variantSize.setIdentifier("variantSize");
            variantSize.setName("Variant Size");
            variantSize.setType("text");
            variantSize.setRequired(false);
            variantSize.setDescription("Variant Size");
            variantSize.setMin("");
            variantSize.setMax("");
            variantSize.setValues(null);

            TableDataBuilder priceField = new TableDataBuilder();
            priceField.setIdentifier("price");
            priceField.setName("price");
            priceField.setType("text");
            priceField.setRequired(true);
            priceField.setDescription("Please Fill Price");
            priceField.setMin("");
            priceField.setMax("");

            TableDataBuilder productMrp = new TableDataBuilder();
            productMrp.setIdentifier("mrp");
            productMrp.setName("Mrp Price");
            productMrp.setType("text");
            productMrp.setRequired(true);
            productMrp.setDescription("Please Fill Mrp Price");
            productMrp.setMin("10");
            productMrp.setMax("10000");

            TableDataBuilder skuId = new TableDataBuilder();
            skuId.setIdentifier("skuId");
            skuId.setName("SKU ID");
            skuId.setType("text");
            skuId.setRequired(true);
            skuId.setDescription("Please Fill Sku Id (optional)");
            skuId.setMin("0");
            skuId.setMax("500");

            TableDataBuilder lengthField = new TableDataBuilder();
            lengthField.setIdentifier("productLength");
            lengthField.setName("product length (inch)");
            lengthField.setType("dropdown");
            lengthField.setRequired(true);
            lengthField.setDescription("Please Fill Product Length");
            lengthField.setMin("2");
            lengthField.setMax("12");
            lengthField.setValues(List.of("5","15","25","300","100"));

            TableDataBuilder  breathField= new TableDataBuilder();
            breathField.setIdentifier("productBreath");
            breathField.setName("product Breath");
            breathField.setType("dropdown");
            breathField.setRequired(true);
            breathField.setDescription("Please Fill Breath");
            breathField.setMin("");
            breathField.setMax("");
            breathField.setValues(List.of("45","50","55","60","65","70","75","80"));

            TableDataBuilder  heightField= new TableDataBuilder();
            heightField.setIdentifier("productHeight");
            heightField.setName("product height");
            heightField.setType("dropdown");
            heightField.setRequired(true);
            heightField.setDescription("Please Fill height");
            heightField.setMin("");
            heightField.setMax("");
            heightField.setValues(List.of("100","101","102","103","106","109","145","1520"));

            List<TableDataBuilder> tableDataBuilders = new ArrayList<>();
            tableDataBuilders.add(variantSize);
            tableDataBuilders.add(priceField);
            tableDataBuilders.add(productMrp);
            tableDataBuilders.add(skuId);
            tableDataBuilders.add(lengthField);
            tableDataBuilders.add(breathField);
            tableDataBuilders.add(heightField);



//            Product Data Form
            ProductDataBuilder productNameField = new ProductDataBuilder();
            productNameField.setIdentifier("productName");
            productNameField.setName("Product Name");
            productNameField.setType("text");
            productNameField.setRequired(true);
            productNameField.setDescription("Please EnterProduct Name");
            productNameField.setMin("10");
            productNameField.setMax("300");

            ProductDataBuilder gstField = new ProductDataBuilder();
            gstField.setIdentifier("gst");
            gstField.setName("Gst %");
            gstField.setType("dropdown");
            gstField.setRequired(true);
            gstField.setDescription("Gst Mandatory");
            gstField.setMin("");
            gstField.setMax("");
            gstField.setValues(List.of("5 %","10 %","12 %","15 %","18 %"));

            ProductDataBuilder hsnField = new ProductDataBuilder();
            hsnField.setIdentifier("hsn");
            hsnField.setName("hsn");
            hsnField.setType("dropdown");
            hsnField.setRequired(true);
            hsnField.setDescription("hsn");
            hsnField.setMin("");
            hsnField.setMax("");
            hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));

            ProductDataBuilder productCode = new ProductDataBuilder();
            productCode.setIdentifier("productCode");
            productCode.setName("Product Code(Optional)");
            productCode.setType("text");
            productCode.setRequired(false);
            productCode.setDescription("product Code");
            productCode.setMin("");
            productCode.setMax("");
            productCode.setValues(null);

            List<ProductDataBuilder> productDataBuilderList = new ArrayList<>();
            productDataBuilderList.add(productNameField);
            productDataBuilderList.add(gstField);
            productDataBuilderList.add(hsnField);
            productDataBuilderList.add(productCode);


            //Product Details
            ProductDataBuilder productStyleField = new ProductDataBuilder();
            productStyleField.setIdentifier("styleName");
            productStyleField.setName("style Name");
            productStyleField.setType("text");
            productStyleField.setRequired(true);
            productStyleField.setDescription("product Style");
            productStyleField.setMin("");
            productStyleField.setMax("");
            productStyleField.setValues(null);

            ProductDataBuilder sleeveTypeField = new ProductDataBuilder();
            sleeveTypeField.setIdentifier("sleeveType");
            sleeveTypeField.setName("Sleeve Type");
            sleeveTypeField.setType("dropdown");
            sleeveTypeField.setRequired(true);
            sleeveTypeField.setDescription("Sleeve");
            sleeveTypeField.setMin("");
            sleeveTypeField.setMax("");
            sleeveTypeField.setValues(List.of("Half Sleeve %","full Sleeve"));

            ProductDataBuilder fitTypeField = new ProductDataBuilder();
            fitTypeField.setIdentifier("fitType");
            fitTypeField.setName("fitType Name");
            fitTypeField.setType("dropdown");
            fitTypeField.setRequired(true);
            fitTypeField.setDescription("fitType please select");
            fitTypeField.setMin("");
            fitTypeField.setMax("");
            fitTypeField.setValues(List.of("Regular Fit","Skin Fit"));

            ProductDataBuilder genderField = new ProductDataBuilder();
            genderField.setIdentifier("gender");
            genderField.setName("gender Name");
            genderField.setType("dropdown");
            genderField.setRequired(true);
            genderField.setDescription("Gender please select");
            genderField.setMin("");
            genderField.setMax("");
            genderField.setValues(List.of("Male","Female","Other"));

            ProductDataBuilder materialTypeField = new ProductDataBuilder();
            materialTypeField.setIdentifier("materialType");
            materialTypeField.setName("material Type");
            materialTypeField.setType("dropdown");
            materialTypeField.setRequired(true);
            materialTypeField.setDescription("material Type please select");
            materialTypeField.setMin("");
            materialTypeField.setMax("");
            materialTypeField.setValues(List.of("Cotton","Satin","Leather","Linen","Denim","Velvet","wool"));

            ProductDataBuilder colorField = new ProductDataBuilder();
            colorField.setIdentifier("productColor");
            colorField.setName("productColor");
            colorField.setType("dropdown");
            colorField.setRequired(true);
            colorField.setDescription("productColor please select");
            colorField.setMin("");
            colorField.setMax("");
            colorField.setValues(List.of("Yellow","Green","Blue","Green","Orange","Velvet","Brown"));

            ProductDataBuilder countryOriginField = new ProductDataBuilder();
            countryOriginField.setIdentifier("country");
            countryOriginField.setName("country");
            countryOriginField.setType("dropdown");
            countryOriginField.setRequired(true);
            countryOriginField.setDescription("country please select");
            countryOriginField.setMin("");
            countryOriginField.setMax("");
            countryOriginField.setValues(List.of("India"));

            ProductDataBuilder patternField = new ProductDataBuilder();
            patternField.setIdentifier("pattern");
            patternField.setName("pattern");
            patternField.setType("dropdown");
            patternField.setRequired(true);
            patternField.setDescription("pattern please select");
            patternField.setMin("");
            patternField.setMax("");
            patternField.setValues(List.of("Line","Circle"));


            ProductDataBuilder manufactureField = new ProductDataBuilder();
            manufactureField.setIdentifier("manufactureName");
            manufactureField.setName("manufactureName");
            manufactureField.setType("text");
            manufactureField.setRequired(false);
            manufactureField.setDescription("country please select");
            manufactureField.setMin("");
            manufactureField.setMax("");
            manufactureField.setValues(null);

            List<ProductDataBuilder> productDetails = new ArrayList<>();
            productDetails.add(productStyleField);
            productDetails.add(sleeveTypeField);
            productDetails.add(sleeveTypeField);
            productDetails.add(fitTypeField);
            productDetails.add(genderField);
            productDetails.add(materialTypeField);
            productDetails.add(colorField);
            productDetails.add(countryOriginField);
            productDetails.add(patternField);
            productDetails.add(manufactureField);


            //Product Description and Other Details
            ProductDataBuilder numberOfItemsField = new ProductDataBuilder();
            numberOfItemsField.setIdentifier("numberOfItems");
            numberOfItemsField.setName("number Of Items");
            numberOfItemsField.setType("dropdown");
            numberOfItemsField.setRequired(true);
            numberOfItemsField.setDescription("numberOfItems please select");
            numberOfItemsField.setMin("");
            numberOfItemsField.setMax("");
            numberOfItemsField.setValues(List.of("1","2","3","4","5","6","7","8","9","10"));

            ProductDataBuilder finishingType = new ProductDataBuilder();
            finishingType.setIdentifier("finishingType");
            finishingType.setName("finishing Type ");
            finishingType.setType("dropdown");
            finishingType.setRequired(true);
            finishingType.setDescription("finishingType please select");
            finishingType.setMin("");
            finishingType.setMax("");
            finishingType.setValues(List.of("Liner","Rarer","Printing","blur shade","shades","multiShades"));

            ProductDataBuilder brandField = new ProductDataBuilder();
            brandField.setIdentifier("brandField");
            brandField.setName("brandField Type ");
            brandField.setType("dropdown");
            brandField.setRequired(false);
            brandField.setDescription("brandField please select");
            brandField.setMin("");
            brandField.setMax("");
            brandField.setValues(List.of("Jack & jones","Microman","Puma","Generic","lux cozi","spyker"));

            ProductDataBuilder descriptionFiled = new ProductDataBuilder();
            descriptionFiled.setIdentifier("description");
            descriptionFiled.setName("description");
            descriptionFiled.setType("textbox");
            descriptionFiled.setRequired(true);
            descriptionFiled.setDescription("please fill description");
            descriptionFiled.setMin("10");
            descriptionFiled.setMax("5000");
            descriptionFiled.setValues(null);

            List<ProductDataBuilder> productDescAndOtherDetails = new ArrayList<>();
            productDescAndOtherDetails.add(numberOfItemsField);
            productDescAndOtherDetails.add(finishingType);
            productDescAndOtherDetails.add(brandField);
            productDescAndOtherDetails.add(descriptionFiled);




            formBuilderRoot.setSizeDataBuilderList(sizeDataBuilderList);
            formBuilderRoot.setTableDataBuilderList(tableDataBuilders);
            formBuilderRoot.setProductDataBuilderList(productDataBuilderList);
            formBuilderRoot.setProductDetailsBuilderList(productDetails);
            formBuilderRoot.setProductDescAndOtherBuilderList(productDescAndOtherDetails);


            return  ResponseEntity.ok(formBuilderRoot);
        }
        else if (categoryId.equals("2")) {
            ProductDataBuilder hsnField = new ProductDataBuilder();
            hsnField.setIdentifier("hsn");
            hsnField.setName("hsn");
            hsnField.setType("dropdown");
            hsnField.setRequired(true);
            hsnField.setDescription("hsn");
            hsnField.setMin("");
            hsnField.setMax("");
            hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));


            ProductDataBuilder netWeightField = new ProductDataBuilder();
            netWeightField.setIdentifier("netWight");
            netWeightField.setName("net Wight in gms");
            netWeightField.setType("text");
            netWeightField.setRequired(true);
            netWeightField.setDescription("netWight");
            netWeightField.setMin("2");
            netWeightField.setMax("10");
            return  ResponseEntity.ok(List.of(hsnField, netWeightField));
        }

        return null;
    }


    @PostMapping("/saveProductData")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> saveProductData(@RequestBody Map<String, Object> formData) {
        // Save the submitted form data
        savedData = formData;
        System.out.println("Form Data: " + formData);
        return ResponseEntity.ok("Form submitted successfully");
    }
    @GetMapping("/getProductData")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Map<String, Object>> getProductData10() {
        // Return the saved data
        System.out.println("Load Data " + savedData);
        return ResponseEntity.ok(savedData);
    }




    ProductRootData productRootData = new ProductRootData();
    @PostMapping("/saveRows")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> saveRows(@RequestBody ProductRootData productRootData) {
        // Save data (you can replace this with database interaction)
        this.productRootData = productRootData;
        System.out.println(this.productRootData);
        return ResponseEntity.ok("Data saved successfully!");
    }


    @GetMapping("/getRows")
    @PreAuthorize("hasRole('SELLER')")
    public ProductRootData getRows() {
        return this.productRootData;
    }


}
