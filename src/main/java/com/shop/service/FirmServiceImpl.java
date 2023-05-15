package com.shop.service;

import com.shop.entity.Firm;
import com.shop.mapper.FirmMapper;
import com.shop.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirmServiceImpl implements FirmService {


    private final FirmRepository firmRepository;
    private final FirmMapper firmMapper;



    @Autowired
    public FirmServiceImpl(FirmRepository firmRepository, FirmMapper firmMapper) {
        this.firmRepository = firmRepository;
        this.firmMapper = firmMapper;

    }


    public List<Firm> getAll(){
        return firmRepository.findAll();
    }

    @Override
    public Firm getById(Long id) {
        return firmRepository.getById(id);
    }
}
