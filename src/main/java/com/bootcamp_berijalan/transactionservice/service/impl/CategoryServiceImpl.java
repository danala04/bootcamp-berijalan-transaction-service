package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResCategoryDto;
import com.bootcamp_berijalan.transactionservice.entity.Category;
import com.bootcamp_berijalan.transactionservice.repository.CategoryRepository;
import com.bootcamp_berijalan.transactionservice.service.CategoryService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public BaseResponseDto getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeletedAtIsNull();  // Assuming soft delete logic
        List<ResCategoryDto> resCategoryDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            resCategoryDtoList.add(
                    new ResCategoryDto(
                            category.getId(),
                            category.getName()
                    )
            );
        }

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.CATEGORIES, resCategoryDtoList);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }
}
