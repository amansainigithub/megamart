package com.coder.springjwt.controllers.admin.userController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.USERS_CONTROLLER)
public class UserControllerByAdmin {
    @Autowired
    private UserService userService;

    @PostMapping(AdminUrlMappings.GET_CUSTOMER_BY_PAGINATION)
    public ResponseEntity<?> getCustomerByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return userService.getCustomerByPagination(page,size);
    }

    @PostMapping(AdminUrlMappings.GET_SELLER_BY_PAGINATION)
    public ResponseEntity<?> getSellerByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return userService.getSellerByPagination(page,size);
    }

    @PostMapping(AdminUrlMappings.GET_ADMIN_BY_PAGINATION)
    public ResponseEntity<?> getAdminByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return userService.getAdminByPagination(page,size);
    }


}
