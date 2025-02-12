package com.coder.springjwt.repository.sellerRepository.catalogRepos;

import com.coder.springjwt.models.sellerModels.catalog.productBrand.ProductBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepo extends JpaRepository<ProductBrandModel,Long> {
}
