package com.shop.service;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import com.shop.dto.ValueFirmFeatureDto;
import com.shop.dto.comment.FirmCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmComment;
import com.shop.exception.FirmNotFoundException;
import com.shop.mapper.CommentMapper;
import com.shop.mapper.FirmMapper;
import com.shop.mapper.ValueFirmFeatureMapper;
import com.shop.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class FirmServiceImpl implements FirmService {


    private final FirmRepository firmRepository;
    private final FirmMapper firmMapper;
    private final ValueFirmFeatureMapper valueFirmFeatureMapper;
    private final CommentMapper commentMapper;


    @Autowired
    public FirmServiceImpl(FirmRepository firmRepository, FirmMapper firmMapper, ValueFirmFeatureMapper valueFirmFeatureMapper, CommentMapper commentMapper) {
        this.firmRepository = firmRepository;
        this.firmMapper = firmMapper;
        this.valueFirmFeatureMapper = valueFirmFeatureMapper;
        this.commentMapper = commentMapper;
    }


    public List<Firm> getAll(){
        return firmRepository.findAll();
    }




    @Override
    public List<BasketFirmDto> getFirmsForBasketByIds(String ids) {
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
