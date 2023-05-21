package com.shop.controllers;

import com.shop.MyUserDetailsService;
import com.shop.dto.FirmDto;
import com.shop.dto.comment.BlogReviewCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmComment;
import com.shop.entity.FirmType;
import com.shop.entity.User;
import com.shop.repository.FirmCommentRepository;
import com.shop.repository.FirmRepository;
import com.shop.repository.UserRepository;
import com.shop.service.FirmService;
import com.shop.service.FirmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class FirmController {

    private final FirmTypeService firmTypeService;
    private final FirmService firmService;
    private final FirmCommentRepository firmCommentRepository;
    private final MyUserDetailsService myUserDetailsService;

    private final UserRepository userRepository;

    private List<FirmDto> topFirm;
    private final FirmRepository firmRepository;


    @Autowired
    public FirmController(
            FirmTypeService firmTypeService,
            FirmService firmService,
            FirmCommentRepository firmCommentRepository, MyUserDetailsService myUserDetailsService, UserRepository userRepository, List<FirmDto> topFirm, FirmRepository firmRepository) {
        this.firmTypeService = firmTypeService;
        this.firmService = firmService;
        this.firmCommentRepository = firmCommentRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.firmRepository = firmRepository;
    }


    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);


    @GetMapping("/1")
    public String getProductsByCategor1(Model model) {

        model.addAttribute("topFirms", topFirm);
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        return "blocks/hea";
    }

    @GetMapping("/product-details/{id}")
    public String getSingleFirm(@PathVariable Long id, Model model) {

        Firm firm = firmService.getById(id);

        model.addAttribute("firm", firm);

        return "product-details";
    }

    @GetMapping
    public String getProductsByCategory(@RequestParam(required = false,defaultValue = "0") int p, Model model) {

        List<FirmDto> firms = firmService.getFirmDtos();

        topFirm = firms.stream().sorted(Comparator.comparing(FirmDto::getRatings)).limit(5)
                .sorted(Comparator.comparing(FirmDto::getRatings).reversed()).collect(Collectors.toList());

        model.addAttribute("topFirm", topFirm);
        model.addAttribute("firms", firms);
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        getProductsByCategor1(model);
        return "index";
    }

    @GetMapping("rating/{num}/{firmId}")
    public RedirectView setRating(@PathVariable(name = "num") Integer num, @PathVariable(name = "firmId") Long firmId, Model model) {

        model.addAttribute("userName", myUserDetailsService.getUserName1());
        Firm firm = firmService.getById(firmId);

        firmService.setRating(firm, num);

        FirmDto firmDto = firmService.getFirmDto(firmId);

        model.addAttribute("firmDto", firmDto);
        return new RedirectView("/firm/" + firmId);
    }


    @GetMapping("firm/{id}")
    public String getFirmById(@PathVariable Long id, Model model) {

        model.addAttribute("userName", myUserDetailsService.getUserName1());
        FirmDto firmDto = firmService.getFirmDto(id);

        model.addAttribute("firmDto", firmDto);
        model.addAttribute("commentDto", new BlogReviewCommentDto());

        return "product-details";
    }

    @GetMapping("category/{categoryId}")
    public String getProductsByCategory(Model model, @NotNull @PathVariable Long categoryId) {

        List<Firm> firms = firmService.getAll().stream().filter(fi -> categoryId.equals(fi.getFirm_type_id()))
                .collect(Collectors.toList());

        FirmType firmType = firmTypeService.getFirmType(firms.get(0).getFirm_type_id());

        model.addAttribute("firms", firms);
        model.addAttribute("type", firmType);
        getProductsByCategor1(model);

        return "firm-category";
    }
    @GetMapping("/registration")
    public String registretion(Model model) {

        return "registration";
    }


    @PostMapping("/registration/add")
    public String registretionAdd(Model model, @RequestParam(value = "s") String userName, @RequestParam(value = "p") String password) {
        userRepository.save(new User(userName, password, true, "USER"));

        return "succes";
    }

    @PostMapping("/firm/{id}")
    public RedirectView createCommentInBlog(@ModelAttribute("review") BlogReviewCommentDto blogReviewCommentDto, @PathVariable Long id) {
        Firm firm = firmService.getById(id);

        firmCommentRepository.save(FirmComment.builder().
                author(myUserDetailsService.getUserName1())
                        .date(LocalDateTime.now())
                        .text(blogReviewCommentDto.getComment())
                        .firm(firm)
                .build());

        return new RedirectView("/firm/" + id);
    }

    @GetMapping("/search")
    public String search(Model model ) {
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        return "search";
    }

    @GetMapping("/search/firm")
    public String searchFirm(Model model, @RequestParam(value = "name") String name) {
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        List<Firm> productsByName = firmRepository.findByName(name);
        model.addAttribute("firms", productsByName);

        return "search";
    }

    @GetMapping("/about_us")
    public String aboutUs(Model model ) {
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        return "about-us";
    }
}
