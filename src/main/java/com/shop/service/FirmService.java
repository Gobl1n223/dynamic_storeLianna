package com.shop.service;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import com.shop.entity.Firm;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FirmService {

    List<Firm> getAll();
    Firm getById(Long id);

    FirmDto getFirmDto(Long id);

    void setRating(Firm firm, Integer num);
    List<FirmDto> getFirmDtos();

}
