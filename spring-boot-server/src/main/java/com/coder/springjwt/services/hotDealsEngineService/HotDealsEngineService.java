package com.coder.springjwt.services.hotDealsEngineService;

import com.coder.springjwt.dtos.sellerDtos.hotDealsDtos.HotDealsEngineDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface HotDealsEngineService {
    ResponseEntity<?> saveHotDealsEngine(HotDealsEngineDto hotDealsEngineDto);

    ResponseEntity<?> getHotDealsEngine(Long engineId);

    ResponseEntity<?> getHotDealsEngines(Integer page, Integer size);

    ResponseEntity<?> deleteHotDealsEngine(Long engineId);

    ResponseEntity<?> updateHotDealsEngine(HotDealsEngineDto hotDealsEngineDto);
}
