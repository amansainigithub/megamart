package com.coder.springjwt.repository.sellerRepository.categories;

import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BornCategoryRepo extends JpaRepository<BornCategoryModel,Long> {


    @Query("SELECT u FROM BornCategoryModel u WHERE u.babyCategoryModel.id = :id")
    List<BornCategoryModel> getBornCategoryListByBabyCategoryId(@Param("id") Long id);


    @Query("SELECT u FROM BornCategoryModel u WHERE u.babyCategoryModel.childCategoryModel.parentCategory.id = :id")
    List<BornCategoryModel> getBornCategoryListByParentCategoryId(@Param("id") Long id , Pageable pageable);


}
