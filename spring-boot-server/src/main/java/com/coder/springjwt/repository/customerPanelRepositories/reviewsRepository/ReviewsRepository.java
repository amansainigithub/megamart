package com.coder.springjwt.repository.customerPanelRepositories.reviewsRepository;

import com.coder.springjwt.models.customerPanelModels.productReviews.ProductReviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<ProductReviews, Long> {
    Page<ProductReviews> findByUserIdOrderByIdDesc(String userId , Pageable pageable);

    Optional<ProductReviews> findByIdAndUserId(long id, String userId);

}
