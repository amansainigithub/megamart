package com.coder.springjwt.repository.adminRepository.catalogRepos;

import com.coder.springjwt.models.adminModels.catalog.catalogSize.CatalogSizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogSizeRepo extends JpaRepository<CatalogSizeModel,Long> {
}
