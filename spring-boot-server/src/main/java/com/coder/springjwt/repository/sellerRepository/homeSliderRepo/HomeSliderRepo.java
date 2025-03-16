package com.coder.springjwt.repository.sellerRepository.homeSliderRepo;

import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeSliderRepo extends JpaRepository<HomeSliderModel,Long> {
}
