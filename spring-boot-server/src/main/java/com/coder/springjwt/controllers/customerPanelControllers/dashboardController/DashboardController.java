package com.coder.springjwt.controllers.customerPanelControllers.dashboardController;

import com.coder.springjwt.constants.customerPanelConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.publicService.dashboardService.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(CustomerUrlMappings.DASHBOARD_CONTROLLER)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(CustomerUrlMappings.GET_DASHBOARD)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getDashboard(@PathVariable String username) {
        return this.dashboardService.getDashboard(username);
    }
}
