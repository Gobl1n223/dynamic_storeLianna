package com.shop.controllers;

import com.shop.dto.CategoryDto;
import com.shop.service.FirmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AboutController {


    private final FirmTypeService firmTypeService;

    @Autowired
    public AboutController(FirmTypeService firmTypeService) {
        this.firmTypeService = firmTypeService;
    }


    @GetMapping("about-us")
    public String getContact(Model model) {

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        return "about-us";
    }
}
