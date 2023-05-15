package com.shop.mapper;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import com.shop.dto.ValueFirmFeatureDto;
import com.shop.dto.comment.FirmCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static com.shop.controllers.FirmController.ONE_HUNDRED;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public interface FirmMapper {


    BasketFirmDto toBasketDto(FirmShortDto firmShortDto, Integer howMany);




    default List<String> rebuildfirmPictures(Firm firm) {
        if (firm.getFirmPictures().isEmpty()){
            return List.of("/assets/img/firm/no-image.jpg");
        }

        return firm.getFirmPictures().stream().map(FirmPicture::getPicture).collect(toList());
    }



}
