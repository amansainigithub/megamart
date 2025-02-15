package com.coder.springjwt.exception.services.sellerServices.gstPercentageService;

import com.coder.springjwt.dtos.sellerDtos.productMetaDtos.GstPercentageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface GstPercentageService {

    ResponseEntity<?> saveGstPercentage(GstPercentageDto gstPercentageDto);

    ResponseEntity<?> getGstPercentage(Integer page, Integer size);
}
