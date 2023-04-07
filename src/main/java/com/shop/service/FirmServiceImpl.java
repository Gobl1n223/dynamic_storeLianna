package com.shop.service;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import com.shop.dto.ValueFirmFeatureDto;
import com.shop.dto.comment.FirmCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmComment;
import com.shop.exception.ProductNotFoundException;
import com.shop.mapper.CommentMapper;
import com.shop.mapper.FirmMapper;
import com.shop.mapper.ValueFirmFeatureMapper;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class FirmServiceImpl implements FirmService {


    private final ProductRepository firmRepository;
    private final FirmMapper firmMapper;
    private final ValueFirmFeatureMapper valueFirmFeatureMapper;
    private final CommentMapper commentMapper;


    @Autowired
    public FirmServiceImpl(ProductRepository firmRepository, FirmMapper firmMapper, ValueFirmFeatureMapper valueFirmFeatureMapper, CommentMapper commentMapper) {
        this.firmRepository = firmRepository;
        this.firmMapper = firmMapper;
        this.valueFirmFeatureMapper = valueFirmFeatureMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public FirmDto getById(Long id) {
        Firm firm = firmRepository.findProductById(id).orElseThrow(ProductNotFoundException::new);


        List<FirmCommentDto> sortedComments = firm.getFirmComment().stream().sorted(Comparator.comparing(FirmComment::getDate)).map(commentMapper::toDto).collect(toList());

        List<ValueFirmFeatureDto> firmFeatures = firm.getValueFirmFeature().stream().map(valueFirmFeatureMapper::toDto).sorted(Comparator.comparing(ValueFirmFeatureDto::getFeatureName)).collect(toList());


        return firmMapper.toDto(firm, firmFeatures, sortedComments);
    }


    @Override
    public List<FirmShortDto> getProductsByName(String name) {

        List<Firm> firms = firmRepository.findByName(name);


        return firms.stream().map(firmMapper::toShortDto).collect(toList());
    }


    @Override
    public List<FirmShortDto> getProductByDiscount(Pageable page) {
        List<Firm> firms = firmRepository.findProductsByDiscount(page);

        if (firms.isEmpty()) throw new ProductNotFoundException("Продукт со скидкой не найден");

        return firms.stream().map(firmMapper::toShortDto).collect(toList());
    }


    @Override
    public List<BasketFirmDto> getProductsForBasketByIds(String ids) {
        if (isNull(ids) || ids.equals("")) return null;

        String[] string = ids.split(",");

        List<Long> idsList = new ArrayList<>();
        for (String s : string) {
            idsList.add(Long.valueOf(s));
        }

        List<Firm> firmList = firmRepository.findAllById(new HashSet<>(idsList));
        if (firmList.isEmpty()) return null;

        Map<Long, Long> resultMap = new HashMap<>();
        idsList.forEach(e -> resultMap.compute(e, (k, v) -> v == null ? 1L : v + 1L));

        List<FirmShortDto> firmShortDtoList = firmList.stream().map(firmMapper::toShortDto).collect(toList());

        return firmShortDtoList.stream().map(p -> firmMapper.toBasketDto(p, resultMap.get(p.getId()).intValue())).collect(toList());
    }
}
