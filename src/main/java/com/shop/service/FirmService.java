package com.shop.service;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FirmService {

     FirmDto getById(Long id);
     List<FirmShortDto> getProductsByName(String name);

     List<FirmShortDto> getProductByDiscount(Pageable page);

     List<BasketFirmDto> getProductsForBasketByIds(String ids);

}
