package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;

import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerCatalogRepository extends JpaRepository<SellerCatalog, Long>{

    @Query(value = "SELECT * FROM seller_catalog WHERE username = :username", nativeQuery = true)
    List<SellerCatalog> findAllByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM seller_catalog WHERE username = :username and catalog_status = :catalog_status",
            nativeQuery = true)
    List<SellerCatalog> findAllByCatalogStatusAndCatalogStatus(@Param("username") String username , @Param("catalog_status") String catalog_status );

}
