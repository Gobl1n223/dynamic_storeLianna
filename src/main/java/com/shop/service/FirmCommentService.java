package com.shop.service;

import com.shop.dto.comment.FirmReviewCommentDto;

public interface FirmCommentService {
     void saveComment(FirmReviewCommentDto firmReviewCommentDto, Long id);
}
