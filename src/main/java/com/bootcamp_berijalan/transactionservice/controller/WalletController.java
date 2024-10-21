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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> saveWallet(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ReqSaveWalletDto request) {
        BaseResponseDto response = walletService.save(userDetails, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> updateWallet(@Valid @RequestBody ReqSaveWalletDto request, @PathVariable Long id) {
        BaseResponseDto response = walletService.update(id, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> getAllWalletByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        BaseResponseDto response = walletService.getAllWalletsByUserId(userDetails);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
