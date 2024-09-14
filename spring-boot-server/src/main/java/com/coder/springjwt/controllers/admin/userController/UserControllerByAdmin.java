package com.coder.springjwt.controllers.admin.userController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.payload.request.LoginRequest;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.adminServices.adminAuthService.AdminAuthService;
import com.coder.springjwt.services.adminServices.userService.UserService;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.AUTH_BASE_URL)
public class UserControllerByAdmin {
    @Autowired
    private UserService userService;

    @PostMapping(AdminUrlMappings.GET_CUSTOMER_BY_PAGINATION)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return userService.getCustomerByPagination(page,size);
    }


}
