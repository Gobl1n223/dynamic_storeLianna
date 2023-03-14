package com.shop.mapper;

import com.shop.dto.comment.BlogCommentDto;
import com.shop.dto.FullBlogDto;
import com.shop.dto.ShortBlogDto;
import com.shop.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    @Mapping(target = "date", expression = "java(changeDateToString(blog))")
    ShortBlogDto toShortDto(Blog blog);

    @Mapping(target = "date", expression = "java(changeDateToString(blog))")
    @Mapping(source = "comments", target = "blogComment")
    FullBlogDto toDto(Blog blog, List<BlogCommentDto> comments);


    default String changeDateToString(Blog blog) {

        return blog.getDate();
    }

}
