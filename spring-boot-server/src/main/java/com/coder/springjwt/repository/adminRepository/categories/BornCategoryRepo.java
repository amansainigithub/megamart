package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BornCategoryRepo extends JpaRepository<BornCategoryModel,Long> {



}
