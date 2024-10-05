package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerBank.SellerBank;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerStoreRepository extends JpaRepository<SellerStore, Long> {
}
