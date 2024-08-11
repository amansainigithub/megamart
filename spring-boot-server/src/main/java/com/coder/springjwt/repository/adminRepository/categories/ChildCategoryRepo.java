package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCategoryRepo extends JpaRepository<ChildCategoryModel,Long> {
}
