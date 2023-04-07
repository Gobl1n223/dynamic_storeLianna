package com.shop.mapper;

import com.shop.dto.comment.BlogCommentDto;
import com.shop.dto.comment.FirmCommentDto;
import com.shop.dto.comment.FirmReviewCommentDto;
import com.shop.entity.BlogComment;
import com.shop.entity.FirmComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "date", expression = "java(changeDateToString(comment.getDate()))")
    BlogCommentDto toDto(BlogComment comment);

    @Mapping(target = "date", expression = "java(changeDateToString(comment.getDate()))")
    FirmCommentDto toDto(FirmComment comment);

    @Mapping(target = "date", expression = "java(setDate())")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "firmReviewCommentDto.comment", target = "text")
    @Mapping(source = "firmReviewCommentDto.name", target = "author")
    FirmComment toEntity(FirmReviewCommentDto firmReviewCommentDto, Long id);

    default String changeDateToString(LocalDateTime date) {

        return String.format("%s %d:%d",
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                date.getHour(),
                date.getMinute());
    }

    default LocalDateTime setDate() {
        return LocalDateTime.now();
    }
}
