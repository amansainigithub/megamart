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
            ProductDataBuilder nameField = new ProductDataBuilder();
            nameField.setIdentifier("name");
            nameField.setName("Product Name");
            nameField.setType("text");
            nameField.setRequired(true);
            nameField.setDescription("Product Name");
            nameField.setMin("");
            nameField.setMax("");

            ProductDataBuilder addressField = new ProductDataBuilder();
            addressField.setIdentifier("address");
            addressField.setName("Address");
            addressField.setType("text");
            addressField.setRequired(true);
            addressField.setDescription("Address");
            addressField.setMin("");
            addressField.setMax("");

            ProductDataBuilder gstField = new ProductDataBuilder();
            gstField.setIdentifier("gst");
            gstField.setName("Gst");
            gstField.setType("dropdown");
            gstField.setRequired(true);
            gstField.setDescription("Gst");
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

            ProductDataBuilder styleCodeField = new ProductDataBuilder();
            styleCodeField.setIdentifier("styleCode");
            styleCodeField.setName("style Code");
            styleCodeField.setType("text");
            styleCodeField.setRequired(false);
            styleCodeField.setDescription("Style code");
            styleCodeField.setMin("");
            styleCodeField.setMax("");

            ProductDataBuilder netWeightField = new ProductDataBuilder();
            netWeightField.setIdentifier("netWight");
            netWeightField.setName("net Wight in gms");
            netWeightField.setType("text");
            netWeightField.setRequired(true);
            netWeightField.setDescription("netWight");
            netWeightField.setMin("");
            netWeightField.setMax("");



            List<ProductDataBuilder> productDataBuilderList = new ArrayList<>();
            productDataBuilderList.add(nameField);
            productDataBuilderList.add(addressField);
            productDataBuilderList.add(gstField);
            productDataBuilderList.add(hsnField);
            productDataBuilderList.add(styleCodeField);
            productDataBuilderList.add(netWeightField);



            //Variation Data Builder
            VariationsDataBuilder varFieldFirst = new VariationsDataBuilder();
            varFieldFirst.setIdentifier("varFieldFirst");
            varFieldFirst.setName("varFieldFirst varFieldFirst in gms");
            varFieldFirst.setType("text");
            varFieldFirst.setRequired(true);
            varFieldFirst.setDescription("varFieldFirst");
            varFieldFirst.setMin("");
            varFieldFirst.setMax("");
            List<VariationsDataBuilder> variationsDataBuilderList = new ArrayList<>();
            variationsDataBuilderList.add(varFieldFirst);


            SizeDataBuilder sizeField = new SizeDataBuilder();
            sizeField.setIdentifier("size");
            sizeField.setName("size");
            sizeField.setType("multi-select");
            sizeField.setRequired(true);
            sizeField.setDescription("Size");
            sizeField.setMin("");
            sizeField.setMax("");
            sizeField.setValues(List.of("S","M", "L", "XXL","3XL"));
            List<SizeDataBuilder> sizeDataBuilderList = new ArrayList<>();
            sizeDataBuilderList.add(sizeField);

            TableDataBuilder priceField = new TableDataBuilder();
            priceField.setIdentifier("price");
            priceField.setName("price");
            priceField.setType("text");
            priceField.setRequired(true);
            priceField.setDescription("Please Fill Price");
            priceField.setMin("");
            priceField.setMax("");

            TableDataBuilder lengthField = new TableDataBuilder();
            lengthField.setIdentifier("length");
            lengthField.setName("length");
            lengthField.setType("text");
            lengthField.setRequired(true);
            lengthField.setDescription("Please Fill Length");
            lengthField.setMin("");
            lengthField.setMax("");

            TableDataBuilder inventoryField = new TableDataBuilder();
            inventoryField.setIdentifier("inventory");
            inventoryField.setName("inventory");
            inventoryField.setType("dropdown");
            inventoryField.setRequired(true);
            inventoryField.setDescription("Please Fill inventory");
            inventoryField.setMin("");
            inventoryField.setMax("");
            inventoryField.setValues(List.of("5","15","25","300","100"));

            TableDataBuilder categoryField = new TableDataBuilder();
            categoryField.setIdentifier("category");
            categoryField.setName("category");
            categoryField.setType("text");
            categoryField.setRequired(true);
            categoryField.setDescription("category");
            categoryField.setMin("");
            categoryField.setMax("");
            categoryField.setValues(null);

            List<TableDataBuilder> tableDataBuilders = new ArrayList<>();
            tableDataBuilders.add(priceField);
            tableDataBuilders.add(lengthField);
            tableDataBuilders.add(inventoryField);
            tableDataBuilders.add(categoryField);

            formBuilderRoot.setProductDataBuilderList(productDataBuilderList);
            formBuilderRoot.setVariationsDataBuilderList(variationsDataBuilderList);
            formBuilderRoot.setSizeDataBuilderList(sizeDataBuilderList);
            formBuilderRoot.setTableDataBuilderList(tableDataBuilders);

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




    private List<RowData> rows = new ArrayList<>();
    @PostMapping("/saveRows")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> saveRows(@RequestBody List<RowData> rowDataList) {

        rows.clear();

        // Save data (you can replace this with database interaction)
        rows.addAll(rowDataList);
        System.out.println(rowDataList);

        System.out.println(this.rows);
        return ResponseEntity.ok("Data saved successfully!");
    }


    @GetMapping("/getRows")
    @PreAuthorize("hasRole('SELLER')")
    public RootRenderData getRows() {

        RootRenderData rootRenderData = new RootRenderData();
        rootRenderData.setName("Aman Saini");
        rootRenderData.setAddress("1234 Address");
        rootRenderData.setGst("12 %");
        rootRenderData.setHsn("145520");
        rootRenderData.setStyleCode("12300");
        rootRenderData.setNetWight("100 kg");

        rootRenderData.setRowData(this.rows);

        System.out.println(this.rows);

        return rootRenderData;
    }


}
