package com.coder.springjwt.repository.sellerRepository.productStatusRepo;

import com.coder.springjwt.models.productStatus.ProductReviewStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewStatusRepository extends JpaRepository<ProductReviewStatusModel,Long> {

    Boolean existsById(long Id);
}
