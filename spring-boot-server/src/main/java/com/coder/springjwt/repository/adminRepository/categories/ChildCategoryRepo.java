package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildCategoryRepo extends JpaRepository<ChildCategoryModel,Long> {


    @Query("SELECT u FROM ChildCategoryModel u WHERE u.parentCategory.id = :id")
    List<ChildCategoryModel> getChildCategoriesListByParentCategoryId(@Param("id") Long id);

}
