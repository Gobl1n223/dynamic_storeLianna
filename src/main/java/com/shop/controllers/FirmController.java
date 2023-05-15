package com.shop.controllers;

import com.shop.MyUserDetailsService;
import com.shop.entity.Firm;
import com.shop.entity.FirmType;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.FirmService;
import com.shop.service.FirmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class FirmController {

    private final FirmTypeService firmTypeService;
    private final FirmService firmService;
    private final MyUserDetailsService myUserDetailsService;

    private final UserRepository userRepository;


    @Autowired
    public FirmController(
            FirmTypeService firmTypeService,
            FirmService firmService,
            MyUserDetailsService myUserDetailsService, UserRepository userRepository) {
        this.firmTypeService = firmTypeService;
        this.firmService = firmService;
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
    }


    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);


    @GetMapping("/1")
    public String getProductsByCategor1(Model model) {
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

        List<Firm> firms = firmService.getAll();
        model.addAttribute("firms", firms);
        model.addAttribute("userName", myUserDetailsService.getUserName1());

        return "index";
    }


    @GetMapping("firm/{id}")
    public String getFirmById(@PathVariable Long id, Model model) {

        Firm firm = firmService.getById(id);
        model.addAttribute("firm", firm);

        return "product-details";
    }

    @GetMapping("category/{categoryId}")
    public String getProductsByCategory(Model model, @NotNull @PathVariable Long categoryId) {

        List<Firm> firms = firmService.getAll().stream().filter(fi -> categoryId.equals(fi.getFirm_type_id()))
                .collect(Collectors.toList());

        FirmType firmType = firmTypeService.getFirmType(firms.get(0).getFirm_type_id());

        model.addAttribute("firms", firms);
        model.addAttribute("type", firmType);

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

}
