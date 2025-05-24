package com.coder.springjwt.repository.productReviewFileRepo;

import com.coder.springjwt.models.customerPanelModels.productReviews.ProductReviewFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewsFilesRepository extends JpaRepository<ProductReviewFiles, Long> {
}
