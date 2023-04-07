package com.shop.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class FirmShortDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final BigDecimal priceWithoutDiscount;
    private final Integer discountPercent;
    private final String picture;
    private final Double rating;
}
