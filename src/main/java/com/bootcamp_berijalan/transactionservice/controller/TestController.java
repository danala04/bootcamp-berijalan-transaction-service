package com.bootcamp_berijalan.transactionservice.controller;

import com.bootcamp_berijalan.transactionservice.util.CustomUserDetails;
import com.bootcamp_berijalan.transactionservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<Long> testAuthentication(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(customUserDetails.getId());
    }
}
