package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasketFirmDto {
    private final FirmShortDto firmShortDto;
    private final Integer howMany;
}
