package com.shop.service;

import com.shop.dto.FirmDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmComment;
import com.shop.entity.Rating;
import com.shop.mapper.FirmMapper;
import com.shop.repository.FirmCommentRepository;
import com.shop.repository.FirmRepository;
import com.shop.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirmServiceImpl implements FirmService {


    private final FirmRepository firmRepository;
    private final FirmMapper firmMapper;

    private final FirmCommentRepository firmCommentRepository;

    private final RatingRepository ratingRepository;



    @Autowired
    public FirmServiceImpl(FirmRepository firmRepository, FirmMapper firmMapper, FirmCommentRepository firmCommentRepository, RatingRepository ratingRepository) {
        this.firmRepository = firmRepository;
        this.firmMapper = firmMapper;

        this.firmCommentRepository = firmCommentRepository;
        this.ratingRepository = ratingRepository;
    }


    public List<Firm> getAll(){
        return firmRepository.findAll();
    }

    @Override
    public Firm getById(Long id) {
        return firmRepository.getById(id);
    }

    public FirmDto getFirmDto(Long id){
        Firm firm = getById(id);

        List<FirmComment> firmComment = firmCommentRepository.findAll().stream()
                .filter(s -> firm.getId().equals(s.getFirm().getId())).collect(Collectors.toList());

        Double rating = getRating(firm.getId());

        NumberFormat nf = new DecimalFormat("#.#");

        return FirmDto.builder()
                .firm(firm)
                .rating(nf.format(rating))
                .ratings(rating)
                .firmComment(firmComment)
                .build();
    }

    public List<FirmDto> getFirmDtos(){
        List<FirmDto> firmDtos = new ArrayList<>();

        List<Firm> firms = firmRepository.findAll();

        for (Firm firm: firms) {
            List<FirmComment> firmComment = firmCommentRepository.findAll().stream()
                    .filter(s -> firm.getId().equals(s.getFirm().getId())).collect(Collectors.toList());

            Double rating = getRating(firm.getId());

            NumberFormat nf = new DecimalFormat("#.#");

            firmDtos.add(FirmDto.builder()
                    .firm(firm)
                    .rating(nf.format(rating))
                            .ratings(rating)
                    .firmComment(firmComment)
                    .build());
        }

        return firmDtos;
    }

    @Override
    public void setRating(Firm firm, Integer num) {

        Rating rating1 = new Rating();
        rating1.setNum(num);
        rating1.setFirm(firm);
        ratingRepository.save(rating1);

    }

    public Double getRating(Long firmId){

        List<Integer> ratings = ratingRepository.findAll()
                .stream().filter(s -> firmId.equals(s.getFirm().getId())).map(Rating::getNum)
                .collect(Collectors.toList());

        Double a = 0.0;

        for (Integer num: ratings) {
            a += num;
        }
         a /= ratings.size();
        return a;
    }
}
