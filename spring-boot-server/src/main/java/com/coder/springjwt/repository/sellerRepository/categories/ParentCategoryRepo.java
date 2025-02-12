package com.coder.springjwt.repository.sellerRepository.categories;

import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentCategoryRepo extends JpaRepository<ParentCategoryModel,Long> {
}
