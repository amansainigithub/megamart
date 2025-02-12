package com.coder.springjwt.repository.sellerRepository.catalogRepos;


import com.coder.springjwt.models.sellerModels.catalog.catalogMaterial.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMaterialRepo extends JpaRepository<ProductMaterial,Long> {
}
