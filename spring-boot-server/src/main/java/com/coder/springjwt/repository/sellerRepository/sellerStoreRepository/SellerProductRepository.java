package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

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

    Page<SellerProduct> findByProductStatusAndSellerUserNameAndVariant(String productStatus , String sellerUserName , String variant , Pageable pageable);

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

}
