package com.coder.springjwt.services.adminServices.userService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    public ResponseEntity<?> getCustomerByPagination(Integer page, Integer size);
}
