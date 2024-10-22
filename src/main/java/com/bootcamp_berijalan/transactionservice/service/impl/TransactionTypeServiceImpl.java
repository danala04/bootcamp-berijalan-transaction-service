package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTransactionTypeDto;
import com.bootcamp_berijalan.transactionservice.entity.TransactionType;
import com.bootcamp_berijalan.transactionservice.repository.TransactionTypeRepository;
import com.bootcamp_berijalan.transactionservice.service.TransactionTypeService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {
    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Override
    public BaseResponseDto getAll() {
        List<TransactionType> transactionTypeList = transactionTypeRepository.findByDeletedAtIsNull();
        List<ResTransactionTypeDto> resTransactionTypeDtoList = new ArrayList<>();

        for (TransactionType transactionType : transactionTypeList) {
            resTransactionTypeDtoList.add(
                    new ResTransactionTypeDto(
                            transactionType.getId(),
                            transactionType.getName()
                    )
            );
        }

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.TRANSACTIONTYPES, resTransactionTypeDtoList);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }
}
