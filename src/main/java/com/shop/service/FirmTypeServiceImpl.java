package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.dto.FirmShortDto;
import com.shop.exception.CategoryNotFoundException;
import com.shop.exception.FirmNotFoundException;
import com.shop.mapper.FirmMapper;
import com.shop.mapper.FirmTypeMapper;
import com.shop.repository.FirmRepository;
import com.shop.repository.FirmTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FirmTypeServiceImpl implements FirmTypeService {


    private final FirmTypeRepository productTypeRepository;
    private final FirmTypeMapper firmTypeMapper;
    private final FirmRepository productRepository;
    private final FirmMapper productMapper;


    @Autowired
    public FirmTypeServiceImpl(
            FirmTypeRepository productTypeRepository,
            FirmTypeMapper firmTypeMapper,
            FirmRepository productRepository,
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
            throw new FirmNotFoundException("Продукт не найден");

        return categoryDtos;
    }

    @Override
    public List<FirmShortDto> getShortByCategory(String category, Pageable page) {
        List<FirmShortDto> firmShortDtos = productRepository.findFirmsByCategory(category, page).stream()
                .map(productMapper::toShortDto)
                .collect(toList());

        if (firmShortDtos.isEmpty())
            throw new CategoryNotFoundException("категория пуста или не существует");

        return firmShortDtos;

    }


}
