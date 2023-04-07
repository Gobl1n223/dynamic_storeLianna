package com.shop.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValueFirmFeatureDto {

    private final String featureName;
    private final String value;
}
