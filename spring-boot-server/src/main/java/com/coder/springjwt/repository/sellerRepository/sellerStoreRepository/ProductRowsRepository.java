package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductRows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRowsRepository extends JpaRepository<ProductRows,Long> {
}
