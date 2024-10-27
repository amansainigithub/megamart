package com.coder.springjwt.repository.adminRepository.catalogRepos;

import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.CatalogNetQuantityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogNetQuantityRepo extends JpaRepository<CatalogNetQuantityModel,Long> {
}
