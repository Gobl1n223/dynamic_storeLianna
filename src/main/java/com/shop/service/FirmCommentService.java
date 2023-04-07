package com.shop.service;

import com.shop.dto.comment.FirmReviewCommentDto;

public interface FirmCommentService {
    public void saveComment(FirmReviewCommentDto firmReviewCommentDto, Long id);
}
