package com.coder.springjwt.services.adminServices.userService.userServiceImple;

import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.services.adminServices.userService.UserService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        Page<User> userData = this.userRepository.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
        log.info("USER DATA " + userData.toString());
        if(!userData.isEmpty()) {

            return ResponseGenerator.generateSuccessResponse(userData, "USER_SUCCESSFULLY_FETCH");
        }else {
            log.info("USER Data Data Not Found its NULL or BLANK ::::::::: {}", "USER DATA" );
            return ResponseGenerator.generateDataNotFound(CustMessageResponse.DATA_NOT_FOUND);
        }
    }
}
