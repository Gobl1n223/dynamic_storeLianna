package com.shop.mapper;


import com.shop.dto.CategoryDto;
import com.shop.entity.FirmType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FirmTypeMapper {

    @Mapping(source = "firmType.picture", target = "picture", defaultExpression = "java(\"/assets/img/firm/no-image.jpg\")")
    @Mapping(source = "firmType.nameCategory", target = "name")
    CategoryDto toDto(FirmType firmType);

}
