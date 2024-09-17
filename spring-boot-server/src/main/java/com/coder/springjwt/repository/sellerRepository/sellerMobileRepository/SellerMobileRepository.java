package com.coder.springjwt.repository.sellerRepository.sellerMobileRepository;

import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerMobileRepository  extends JpaRepository<SellerMobile,Long> {


    Optional<SellerMobile> findByMobile(String mobile);

}
