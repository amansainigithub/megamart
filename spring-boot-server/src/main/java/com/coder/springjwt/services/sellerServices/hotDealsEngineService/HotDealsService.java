package com.coder.springjwt.services.sellerServices.hotDealsEngineService;

import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface HotDealsService {

    ResponseEntity<?> saveHotDealsService(HotDealsDto hotDealsDto);

    ResponseEntity<?> deleteHotDeal(Long id);

    ResponseEntity<?> getHotDeals();

    ResponseEntity<?> updateHotDeals(HotDealsDto hotDealsDto);

    ResponseEntity<?> getHotDeal(Long id);

    ResponseEntity<?> deleteAllHotDeals();

    ResponseEntity<?> updateHotDealFile(MultipartFile file, Long dealId);
}
