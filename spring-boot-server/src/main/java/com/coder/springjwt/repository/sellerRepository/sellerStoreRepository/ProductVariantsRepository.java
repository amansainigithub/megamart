package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantsRepository extends JpaRepository<ProductVariants,Long> {

    boolean existsBySkuId(String skuId);

    ProductVariants findByIdAndProductLabel(long id , String productLabel);

    @Query("SELECT pv FROM ProductVariants pv WHERE pv.productLabel = :label AND pv.sellerProduct.id = :productId")
    ProductVariants findByLabelAndProductId(@Param("label") String label, @Param("productId") String productId);



}
