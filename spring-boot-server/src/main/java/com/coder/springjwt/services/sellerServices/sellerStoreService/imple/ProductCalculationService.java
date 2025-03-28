package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;


import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormProductVariantBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateRandomNumber;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductCalculationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductVariantsRepository productVariantsRepository;


    public Map<String, List<FormProductVariantBuilder>> groupingProductVariats(ProductRootBuilder productRootBuilder){
        Map<String, List<FormProductVariantBuilder>> groupedByColorVariant = productRootBuilder.getProductVariants().stream()
                .collect(Collectors.groupingBy(variant -> variant.getColorVariant()));
        return groupedByColorVariant;
    }



    public void calculateTaxes(String productColor, List<ProductVariants> productVariants, String gstWithPercent, BornCategoryModel bornCategoryModel){
        try {
            double tcsCharge = Double.parseDouble(bornCategoryModel.getTcsCharge());
            double tdsCharge = Double.parseDouble(bornCategoryModel.getTdsCharge());
            double commissionFeeCharge = Double.parseDouble(bornCategoryModel.getCommissionFeesCharge());
            String gst = gstWithPercent.replace("%", "");

            for(ProductVariants pv : productVariants){
                double productPrice = Double.parseDouble(pv.getProductPrice());
                double productMrp = Double.parseDouble(pv.getProductMrp());

                //Set Color Variant
                if(productColor != null){
                    pv.setColorVariant(productColor);
                }

                double grossServiceTax = this.calculateGST(productPrice, Double.parseDouble(gst));
                log.info("grossServiceTax:: " + grossServiceTax);

                double tcs = this.calculateTCS(productPrice, Double.parseDouble(gst),tcsCharge);
                log.info("tcs:: " + tcs);

                double tds = this.calculateTDS(productPrice,tdsCharge);
                log.info("tds:: " + tds);

                double totalPrice = this.calculateTotalPrice(productPrice, grossServiceTax , tcs ,tds , commissionFeeCharge);
                log.info("totalPrice:: " + totalPrice);

                log.info("-------------------------------------------------");
                pv.setCalculatedGst(String.valueOf(roundToTwoDecimalPlaces(grossServiceTax)));
                pv.setCalculatedTcs(String.valueOf(roundToTwoDecimalPlaces(tcs)));
                pv.setCalculatedTds(String.valueOf(roundToTwoDecimalPlaces(tds)));
                pv.setCalculatedTotalPrice(String.valueOf(roundToTwoDecimalPlaces(totalPrice)));

                //Calculation for Discount
                String calculatedDiscount = calculateDiscount(productPrice,productMrp);

                pv.setCalculatedDiscount(calculatedDiscount);

                //calculation for bank settlement Amount
                double bankSettlementAmount = productPrice -(grossServiceTax + tcs + tds + commissionFeeCharge);
                pv.setBankSettlementAmount(String.valueOf(bankSettlementAmount));
                log.info("BankSettlementAmount :: " + bankSettlementAmount);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getSkuCode() {
        String skuCode = null; // Initialize skuCode to avoid uninitialized variable issues
        try {
            do {
                skuCode = generateSkuCode();
            } while (productVariantsRepository.existsBySkuId(skuCode));
        } catch (Exception e) {
            log.error("Error generating SKU code: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(SellerMessageResponse.UNABLE_TO_GENERATE_SKU, e);
        }
        return skuCode;
    }
    public String generateSkuCode() {
        // Example SKU generation logic
        return "SKU-" + UUID.randomUUID().toString().substring(0, 30).toUpperCase();
    }

    // Method to calculate GST,TCS,TDS,TOTAL PRICE
    public double calculateGST(double price, double gstPercentage) {
        return roundToTwo((price * gstPercentage) / 100);
    }

    // Method to calculate TCS (1% of the total price including GST)
    public double calculateTCS(double price, double gstPercentage ,double tcs) {
        double gstAmount = calculateGST(price, gstPercentage);
        double totalPrice = price + gstAmount;
        return roundToTwo((totalPrice * tcs) / 100); // TCS rate is 1%
    }

    // Method to calculate TDS (2% of the product price excluding GST)
    public double calculateTDS(double price, double tds) {
        return roundToTwo((price * tds) / 100); // TDS rate is 2%
    }

    // Utility method to round to two decimal places
    private double roundToTwo(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Method to get the total price including GST, TCS, and TDS
    public double calculateTotalPrice(double price, double gst, double tcs , double tds,double commissionFeeCharge) {
        return price + gst + tcs + tds + commissionFeeCharge;
    }

    public Double roundToTwoDecimalPlaces(Double value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public String calculateDiscount(double sellingPrice , double mrp) {
        if (mrp <= 0) {
            log.info("MRP should be greater than 0");
            throw new RuntimeException("MRP should be greater than 0");
        }
        if ( sellingPrice > mrp) {
            log.info("Selling price cannot be greater than MRP");
            throw new RuntimeException("Selling price cannot be greater than MRP");
        }

        // Calculate discount percentage
        double discountPercentage = ((mrp - sellingPrice) / mrp) * 100;

        // Round to 2 decimal places
        BigDecimal roundedDiscount = BigDecimal.valueOf(discountPercentage).setScale(2, RoundingMode.HALF_UP);

        log.info("Discount Percentage: " + roundedDiscount + "%");

        return roundedDiscount.toPlainString() + "%";
    }

    public String getCurrentDate() // d MMM yyyy
    {
        LocalDate currentDate = LocalDate.now();
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        return currentDate.format(formatter);
    }
    public String getCurrentTime()
    {
        LocalTime currentTime = LocalTime.now();
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return currentTime.format(formatter);
    }

    // Helper method to validate file type
    private boolean isValidImageFormat(String contentType) {
        return contentType != null &&
                (contentType.equals("image/png") || contentType.equals("image/jpeg"));
    }

    public String generateProductId(){
        String productId = GenerateRandomNumber.generateRandomNumber(20)
                +"-"+GenerateRandomNumber.generateRandomNumber(5)
                + "-" + "100000000" +"-"+GenerateRandomNumber.generateRandomNumber(2);
        return productId.toUpperCase();
    }


    public  void setSellerUsernameAndUserId(SellerProduct sellerProduct){
        try {

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = userRepository.findByUsername(currentUser).orElseThrow(() ->
                        new UsernameNotFoundException("UserName Not Found"));

            sellerProduct.setSellerUserName(user.getUsername());
            sellerProduct.setSellerUserId(String.valueOf(user.getId()));
            log.info("Seller SellerUsername and SellerUserId Saved Success in Object");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generateProductCode(int length){
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(randomIndex));
        }
        return "PR-CODE-" + builder.toString();
    }


}
