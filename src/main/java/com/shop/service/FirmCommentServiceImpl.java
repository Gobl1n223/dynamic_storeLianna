package com.shop.service;

import com.shop.dto.comment.FirmReviewCommentDto;
import com.shop.entity.FirmComment;
import com.shop.exception.FirmNotFoundException;
import com.shop.mapper.CommentMapper;
import com.shop.repository.FirmCommentRepository;
import com.shop.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirmCommentServiceImpl implements FirmCommentService {
    private final FirmCommentRepository firmCommentRepository;
    private final CommentMapper commentMapper;
    private final FirmRepository firmRepository;


    @Autowired
    public FirmCommentServiceImpl(FirmCommentRepository firmCommentRepository, CommentMapper commentMapper,
                                  FirmRepository firmRepository
    ) {
        this.firmCommentRepository = firmCommentRepository;
        this.commentMapper = commentMapper;
        this.firmRepository = firmRepository;
    }


    @Override
    public void saveComment(FirmReviewCommentDto firmReviewCommentDto, Long id) {
        FirmComment firmComment = commentMapper.toEntity(firmReviewCommentDto, id);
        firmComment.setFirm(firmRepository.findById(id).orElseThrow(FirmNotFoundException::new));
        firmCommentRepository.save(firmComment);
    }
}
