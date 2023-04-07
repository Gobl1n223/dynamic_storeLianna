package com.shop.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FirmCommentDto {
    private Long id;

    private final String author;

    private final String date;

    private final String text;

    private final Integer rating;

}
