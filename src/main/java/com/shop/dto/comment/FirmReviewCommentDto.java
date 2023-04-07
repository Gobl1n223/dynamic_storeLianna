package com.shop.dto.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FirmReviewCommentDto {
    String comment;
    String name;
    Integer rating;
}
