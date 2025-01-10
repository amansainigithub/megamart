package com.coder.springjwt.repository.adminRepository.productStatusRepo;

import com.coder.springjwt.models.adminModels.productStatus.ProductReviewStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewStatusRepository extends JpaRepository<ProductReviewStatusModel,Long> {

    Boolean existsById(long Id);
}
