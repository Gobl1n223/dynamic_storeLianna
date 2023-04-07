package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.dto.FirmShortDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FirmTypeService {

    List<CategoryDto> getCategories();

    List<FirmShortDto> getShortByCategory(String category, Pageable page);

}
