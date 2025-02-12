package com.coder.springjwt.repository.sellerRepository.catalogRepos;

import com.coder.springjwt.models.sellerModels.catalog.catalogNetQuantity.ProductNetQuantityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductNetQuantityRepo extends JpaRepository<ProductNetQuantityModel,Long> {
}
