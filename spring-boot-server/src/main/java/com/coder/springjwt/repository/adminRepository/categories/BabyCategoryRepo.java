package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BabyCategoryRepo extends JpaRepository<BabyCategoryModel,Long> {

    @Query("SELECT u FROM BabyCategoryModel u WHERE u.childCategoryModel.id = :id")
    List<BabyCategoryModel> getBabyCategoryListByChildCategoryId(@Param("id") Long id);

}
