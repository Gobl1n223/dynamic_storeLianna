package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.dto.FirmShortDto;
import com.shop.entity.FirmType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FirmTypeService {

    FirmType getFirmType(Long id);


}
