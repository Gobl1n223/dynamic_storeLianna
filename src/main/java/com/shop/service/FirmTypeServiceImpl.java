package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.dto.FirmShortDto;
import com.shop.exception.CategoryNotFoundException;
import com.shop.exception.ProductNotFoundException;
import com.shop.mapper.FirmMapper;
import com.shop.mapper.FirmTypeMapper;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FirmTypeServiceImpl implements FirmTypeService {


    private final ProductTypeRepository productTypeRepository;
    private final FirmTypeMapper firmTypeMapper;
    private final ProductRepository productRepository;
    private final FirmMapper productMapper;


    @Autowired
    public FirmTypeServiceImpl(
            ProductTypeRepository productTypeRepository,
            FirmTypeMapper firmTypeMapper,
            ProductRepository productRepository,
            FirmMapper productMapper
    ) {
        this.productTypeRepository = productTypeRepository;
        this.firmTypeMapper = firmTypeMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryDto> categoryDtos = productTypeRepository.findAll().stream()
                .map(firmTypeMapper::toDto).collect(toList());

        if (categoryDtos.isEmpty())
            throw new ProductNotFoundException("Продукт не найден");

        return categoryDtos;
    }

    @Override
    public List<FirmShortDto> getShortByCategory(String category, Pageable page) {
        List<FirmShortDto> firmShortDtos = productRepository.findProductsByCategory(category, page).stream()
                .map(productMapper::toShortDto)
                .collect(toList());

        if (firmShortDtos.isEmpty())
            throw new CategoryNotFoundException("категория пуста или не существует");

        return firmShortDtos;

    }


}
