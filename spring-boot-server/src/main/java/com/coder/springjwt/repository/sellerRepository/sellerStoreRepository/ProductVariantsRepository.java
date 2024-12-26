package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantsRepository extends JpaRepository<ProductVariants,Long> {

    boolean existsBySkuId(String skuId);


}
