package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;


import com.coder.springjwt.formBuilderTools.formVariableKeys.FormProductVariantBuilder;
import com.coder.springjwt.formBuilderTools.formVariableKeys.ProductRootBuilder;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateRandomNumber;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.ProductVariantsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductCalculationService {

    @Autowired
    private ProductVariantsRepository productVariantsRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private BornCategoryRepo bornCategoryRepo;



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
                System.out.println("grossServiceTax:: " + grossServiceTax);

                double tcs = this.calculateTCS(productPrice, Double.parseDouble(gst),tcsCharge);
                System.out.println("tcs:: " + tcs);

                double tds = this.calculateTDS(productPrice,tdsCharge);
                System.out.println("tds:: " + tds);

                double totalPrice = this.calculateTotalPrice(productPrice, grossServiceTax , tcs ,tds , commissionFeeCharge);
                System.out.println("totalPrice:: " + totalPrice);

                System.out.println("-------------------------------------------------");
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
            System.err.println("Error generating SKU code: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Unable to generate unique SKU code", e);
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

    public String calculateDiscount(double mrp, double sellingPrice) {
        if (mrp <= 0) {
            log.info("MRP should be greater than 0");
            return "MRP should be greater than 0";
        }
        // Calculate discount percentage
        double discountPercentage = ((mrp - sellingPrice) / mrp) * 100;

        // Round to 2 decimal places
        BigDecimal roundedDiscount = new BigDecimal(discountPercentage).setScale(2, RoundingMode.HALF_UP);

        log.info("Discount Percentage: " + roundedDiscount + "%");

        return String.valueOf(roundedDiscount);
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





}
