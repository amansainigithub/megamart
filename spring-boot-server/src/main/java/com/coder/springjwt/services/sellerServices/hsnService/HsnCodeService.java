package com.coder.springjwt.services.sellerServices.hsnService;

import com.coder.springjwt.dtos.sellerDtos.hsn.HsnCodesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface HsnCodeService {

    ResponseEntity<?> saveHsn(HsnCodesDto hsnCodesDto);


    ResponseEntity<?> deleteHsnCode(long hsnCodeId);

    ResponseEntity<?> getHsnCodeById(long hsnCodeId);

    ResponseEntity<?> updateHsnCode(HsnCodesDto hsnCodesDto);

    ResponseEntity<?> getHsnCodesPagination(Integer page, Integer size);
}
