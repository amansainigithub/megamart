package com.coder.springjwt.repository.sellerRepository.categories;

import com.coder.springjwt.models.sellerModels.categories.FormBuilderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormBuilderRepo extends JpaRepository<FormBuilderModel,Long> {

}
