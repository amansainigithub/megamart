package com.coder.springjwt.services.sellerServices.homeSliderService;

import com.coder.springjwt.dtos.sellerDtos.homeSliderDtos.HomeSliderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface HomeSliderService {
    ResponseEntity<?> saveHomeSlider(HomeSliderDto homeSliderDto);

    ResponseEntity<?> updateHomeSliderFile(MultipartFile file, Long homeSliderId);

    ResponseEntity<?> deleteHomeSlider(Long homeSliderId);

    ResponseEntity<?> getHomeSliderById(long homeSliderId);

    ResponseEntity<?> getHomeSliderList();

    ResponseEntity<?> updateHomeSlider(HomeSliderDto homeSliderDto);
}
