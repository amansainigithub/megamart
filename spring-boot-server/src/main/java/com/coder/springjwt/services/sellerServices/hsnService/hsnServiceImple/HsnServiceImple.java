package com.coder.springjwt.services.sellerServices.hsnService.hsnServiceImple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.hsn.HsnCodesDto;
import com.coder.springjwt.exception.customerPanelException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.repository.sellerRepository.catalogRepos.HsnRepository;
import com.coder.springjwt.services.sellerServices.hsnService.HsnCodeService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.userService.userServiceImple.UserServiceImple;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HsnServiceImple implements HsnCodeService {


    @Autowired
    private HsnRepository hsnRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> saveHsn(HsnCodesDto hsnCodesDto) {
        MessageResponse response =new MessageResponse();
        try {
                HsnCodes hsnCodes=  modelMapper.map(hsnCodesDto , HsnCodes.class);
                log.info("Object Mapper Convert Success");

                this.hsnRepository.save(hsnCodes);
                log.info("HSN Saved Success");

                response.setMessage(SellerMessageResponse.HSN_SAVED);
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);

        }
        catch (DataIntegrityViolationException ex) {
            response.setMessage(SellerMessageResponse.DUPLICATE_ENTRY_ERROR);
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteHsnCode(long hsnCodeId) {
            try {
                HsnCodes hsnCode = this.hsnRepository.findById(hsnCodeId).orElseThrow(
                        () -> new CategoryNotFoundException(SellerMessageResponse.ID_NOT_FOUND));

                this.hsnRepository.deleteById(hsnCode.getId());
                log.info("Delete Success => HSN id :: " + hsnCodeId );
                return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.DELETE_SUCCESS , SellerMessageResponse.SUCCESS);

            }
            catch (Exception e)
            {
                e.printStackTrace();
                log.error("HSN Code Could Not be deleted");
                return ResponseGenerator.generateBadRequestResponse
                        (SellerMessageResponse.HSN_NOT_DELETE , SellerMessageResponse.FAILED);
            }
        }

    @Override
    public ResponseEntity<?> getHsnCodeById(long hsnCodeId) {
        try {
            HsnCodes  hsnCodes= this.hsnRepository.findById(hsnCodeId).orElseThrow(
                    () -> new RuntimeException(SellerMessageResponse.DATA_NOT_FOUND));
            HsnCodesDto hsnCodesDto = modelMapper.map(hsnCodes, HsnCodesDto.class);
            log.info("Hsn Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(hsnCodesDto , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch Hsn Code By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateHsnCode(HsnCodesDto hsnCodesDto) {
        try {
            log.info("Update Child Process Starting....");
            log.info("ID :: "  + hsnCodesDto.getId());
            this.hsnRepository.findById(hsnCodesDto.getId())
                                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            HsnCodes hsnCodes =  modelMapper.map(hsnCodesDto , HsnCodes.class);
            this.hsnRepository.save(hsnCodes);

            log.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(SellerMessageResponse.SUCCESS ,
                    SellerMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED ,SellerMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getHsnCodesPagination(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<HsnCodes> hsnCodes = this.hsnRepository.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(hsnCodes.isEmpty())
            {
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(SellerMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(hsnCodes, SellerMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(response, SellerMessageResponse.FAILED);
        }
    }

}
