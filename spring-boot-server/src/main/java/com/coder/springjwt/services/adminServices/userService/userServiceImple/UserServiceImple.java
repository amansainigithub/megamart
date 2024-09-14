package com.coder.springjwt.services.adminServices.userService.userServiceImple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.services.adminServices.userService.UserService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImple implements UserService {

    @Autowired
    BabyCategoryRepo babyCategoryRepo;

    @Autowired
    BornCategoryRepo bornCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> getCustomerByPagination(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<User> userData = this.userRepository.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(userData.isEmpty())
            {
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage("Data Not Found");
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());
                return ResponseGenerator.generateBadRequestResponse(response, "DATA NOT FOUND");
            }else{
                response.setStatus(HttpStatus.OK);
                response.setMessage("Data FETCH SUCCESS");
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                return ResponseGenerator.generateSuccessResponse(userData, "DATA FETCH SUCCESS");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Data Not Found");
            return ResponseGenerator.generateBadRequestResponse(response, "DATA NOT FOUND");
        }

    }
}
