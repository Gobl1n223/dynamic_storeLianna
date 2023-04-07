package com.shop.mapper;

import com.shop.dto.ValueFirmFeatureDto;
import com.shop.entity.ValueFirmFeature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ValueFirmFeatureMapper {

    @Mapping(source = "valueFirmFeature.firmFeature.name", target = "featureName")
    ValueFirmFeatureDto toDto(ValueFirmFeature valueFirmFeature);

}
