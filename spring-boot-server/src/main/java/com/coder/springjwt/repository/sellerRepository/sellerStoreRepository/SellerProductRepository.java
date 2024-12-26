package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

    @Query(value = "SELECT * FROM seller_product WHERE id = :id", nativeQuery = true)
    SellerProduct findMyProductId(@Param("id") Long id);
}
