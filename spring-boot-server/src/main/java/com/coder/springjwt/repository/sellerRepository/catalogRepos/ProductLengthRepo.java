package com.coder.springjwt.repository.sellerRepository.catalogRepos;

import com.coder.springjwt.models.sellerModels.catalog.catalogLength.ProductLengthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLengthRepo extends JpaRepository<ProductLengthModel,Long> {
}
