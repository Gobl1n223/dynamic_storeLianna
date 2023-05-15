package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.entity.FirmType;
import com.shop.exception.FirmNotFoundException;
import com.shop.mapper.FirmMapper;
import com.shop.repository.FirmRepository;
import com.shop.repository.FirmTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FirmTypeServiceImpl implements FirmTypeService {


    private final FirmTypeRepository productTypeRepository;
    private final FirmRepository productRepository;
    private final FirmMapper productMapper;


    @Autowired
    public FirmTypeServiceImpl(
            FirmTypeRepository productTypeRepository,
            FirmRepository productRepository,
            FirmMapper productMapper
    ) {
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public FirmType getFirmType(Long id) {
        return productTypeRepository.getById(id);
    }


}
