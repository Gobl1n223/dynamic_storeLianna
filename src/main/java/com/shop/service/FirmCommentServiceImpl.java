package com.shop.service;

import com.shop.dto.comment.FirmReviewCommentDto;
import com.shop.entity.FirmComment;
import com.shop.exception.ProductNotFoundException;
import com.shop.mapper.CommentMapper;
import com.shop.repository.ProductCommentRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirmCommentServiceImpl implements FirmCommentService {
    private final ProductCommentRepository productCommentRepository;
    private final CommentMapper commentMapper;
    private final ProductRepository productRepository;


    @Autowired
    public FirmCommentServiceImpl(ProductCommentRepository productCommentRepository, CommentMapper commentMapper,
                                  ProductRepository productRepository
    ) {
        this.productCommentRepository = productCommentRepository;
        this.commentMapper = commentMapper;
        this.productRepository = productRepository;
    }


    @Override
    public void saveComment(FirmReviewCommentDto firmReviewCommentDto, Long id) {
        FirmComment firmComment = commentMapper.toEntity(firmReviewCommentDto, id);
        firmComment.setFirm(productRepository.findById(id).orElseThrow(ProductNotFoundException::new));
        productCommentRepository.save(firmComment);
    }
}
