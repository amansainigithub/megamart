package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.BornCategorySampleFilesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BornSampleFilesRepo extends JpaRepository<BornCategorySampleFilesModel,Long> {
}
