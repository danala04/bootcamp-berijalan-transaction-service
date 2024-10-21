package com.bootcamp_berijalan.transactionservice.controller;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveWalletDto;
import com.bootcamp_berijalan.transactionservice.service.WalletService;
import com.bootcamp_berijalan.transactionservice.util.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity saveWallet(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ReqSaveWalletDto request) {
        BaseResponseDto response = walletService.save(userDetails, request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
