package com.bootcamp_berijalan.transactionservice.controller;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransferDto;
import com.bootcamp_berijalan.transactionservice.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> save(@Valid @RequestBody ReqSaveTransferDto reqSaveTransferDto) {
        BaseResponseDto response = transferService.save(reqSaveTransferDto);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
