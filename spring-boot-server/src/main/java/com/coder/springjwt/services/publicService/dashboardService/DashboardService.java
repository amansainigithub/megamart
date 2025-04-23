package com.coder.springjwt.services.publicService.dashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface DashboardService  {
    ResponseEntity<?> getDashboard(String username);
}
