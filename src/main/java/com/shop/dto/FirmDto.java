package com.shop.dto;


import com.shop.dto.comment.FirmCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmComment;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class FirmDto {

    private final Firm firm;
    private final String rating;
    private final Double ratings;
    private final List<FirmComment> firmComment;
}
