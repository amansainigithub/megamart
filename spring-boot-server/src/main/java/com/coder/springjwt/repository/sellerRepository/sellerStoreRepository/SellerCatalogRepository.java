package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerCatalogRepository extends JpaRepository<SellerCatalog, Long>{
}
