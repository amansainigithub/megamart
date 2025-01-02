package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
