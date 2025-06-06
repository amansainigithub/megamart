package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

    @Query(value = "SELECT * FROM seller_product WHERE id = :id", nativeQuery = true)
    SellerProduct findMyProductId(@Param("id") Long id);

    @Query(value = "SELECT * FROM seller_product WHERE variant = :variant", nativeQuery = true)
    List<SellerProduct> findByVariant(@Param("variant") Long variant);

    @Query(value = "SELECT * FROM seller_product WHERE  seller_store_name = :sellerStoreName and product_status = :completed  OR product_status = :inCompleted",
            nativeQuery = true)
    Page<SellerProduct> findAllByProductWithPendingStatus( @Param("sellerStoreName") String sellerStoreName ,
                                                                 @Param("completed") String completed ,
                                                                 @Param("inCompleted") String inCompleted ,
                                                                 Pageable pageable);

    @Query(value = "SELECT * FROM seller_product WHERE id = :id and product_status = :productStatus", nativeQuery = true)
    Optional<SellerProduct> findByProductWithProductStatus(@Param("id") Long id , @Param("productStatus") String productStatus);

    @Query(value = "SELECT * FROM seller_product WHERE parent_key = :parent_key" , nativeQuery = true)
    List<SellerProduct> findByParentKey(@Param("parent_key") String parent_key);


    @Query(value = "SELECT * FROM seller_product WHERE variant = :variant and product_status = :product_status", nativeQuery = true)
    List<SellerProduct> findByVariantAndProductStatus(@Param("variant") Long variant , @Param("product_status") String product_status);


    // Native SQL query
    @Query(value = "SELECT * FROM seller_product WHERE product_status = :productStatus and seller_user_name = :sellerUsername and " +
            "variant IN (:variants)", nativeQuery = true)
    Page<SellerProduct> findByVariantIn(@Param("productStatus") String productStatus ,
                                        @Param("sellerUsername") String sellerUsername ,
                                        @Param("variants") List<String> variants,
                                        Pageable pageable);


    @Query(value = "SELECT * FROM seller_product WHERE product_status = :completed  OR product_status = :inCompleted",
            nativeQuery = true)
    Page<SellerProduct> findProductListForVerificationByAdmin( @Param("completed") String completed ,
                                                           @Param("inCompleted") String inCompleted ,
                                                           Pageable pageable);

    @Query(value = "SELECT * FROM seller_product WHERE product_status = :productStatus and " + "variant IN (:variants)", nativeQuery = true)
    Page<SellerProduct> findByProductUnderReviewByAdmin(@Param("productStatus") String productStatus ,
                                        @Param("variants") List<String> variants,
                                        Pageable pageable);


    Boolean existsByProductLauncherId( String productLauncherId);

    @Query(value = "SELECT * FROM seller_product WHERE product_status IN (:productStatus) and " + "variant IN (:variants)", nativeQuery = true)
    Page<SellerProduct> findByProductUnderReviewByAdmin(@Param("productStatus") List<String> productStatus ,
                                                        @Param("variants") List<String> variants,
                                                        Pageable pageable);


    Page<SellerProduct> findByBabyCategoryIdAndProductStatus(
            String id, String productStatus,Pageable pageable);

    Page<SellerProduct> findByBornCategoryIdAndProductStatus(
            String id, String productStatus, Pageable pageable);


    SellerProduct findByIdAndProductStatus(long id, String productStatus);

    Page<SellerProduct> findByBornCategoryId(String id , Pageable pageable);




    @Query("SELECT u FROM SellerProduct u LEFT JOIN u.productRows pr WHERE u.id = :id AND pr.id = :sizeId AND pr.productLabel = :productLabel")
    SellerProduct getSellerProductData(@Param("id") Long id,@Param("sizeId") Long sizeId, @Param("productLabel") String productLabel);


    @Query("SELECT pr FROM SellerProduct u JOIN u.productRows pr WHERE u.id = :id AND pr.id = :sizeId AND pr.productLabel = :productLabel")
    Optional<ProductVariants> getExactProductVariant(@Param("id") Long id, @Param("sizeId") Long sizeId, @Param("productLabel") String productLabel);




//    Filters Query Starting
    @Query("SELECT p FROM SellerProduct p WHERE p.brandField IN :brandField")
    Page<SellerProduct> findByBrandField(List<String> brandField, Pageable pageable);

    @Query("SELECT p FROM SellerProduct p WHERE p.brandField IN :brandField AND p.gender IN :genders")
    Page<SellerProduct> findByBrandFieldAndGenders(List<String> brandField, List<String> genders , Pageable pageable);

    @Query("SELECT p FROM SellerProduct p WHERE p.gender IN :genders")
    Page<SellerProduct> findByGenders(List<String> genders , Pageable pageable);

    @Query("SELECT p FROM SellerProduct p WHERE p.productFPrice BETWEEN :minPrice AND :maxPrice")
    Page<SellerProduct> findByPriceRange(
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            Pageable pageable);

    @Query("SELECT p FROM SellerProduct p WHERE p.brandField IN :brandField AND p.productFPrice BETWEEN :minPrice AND :maxPrice")
    Page<SellerProduct> findByBrandFieldAndPriceRange(
            @Param("brandField") List<String> brandField,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            Pageable pageable);

    @Query("SELECT p FROM SellerProduct p WHERE " +
            "p.brandField IN :brandField AND " +
            "p.gender IN :gender " +
            "AND p.productFPrice BETWEEN :minPrice AND :maxPrice")
    Page<SellerProduct> findByBrandFieldAndGenderAndPriceRange(
            @Param("brandField") List<String> brandField,
            @Param("gender") List<String> gender,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            Pageable pageable);
    //    Filters Query Ending



    @Query("SELECT COUNT(pr) FROM SellerProduct pr WHERE pr.productStatus = :status")
    long countAllDeliveriesByStatus(@Param("status") String status);





}
