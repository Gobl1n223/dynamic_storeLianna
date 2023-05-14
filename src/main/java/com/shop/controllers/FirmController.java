package com.shop.controllers;

import com.shop.dto.BasketFirmDto;
import com.shop.dto.CategoryDto;
import com.shop.dto.FirmDto;
import com.shop.dto.FirmShortDto;
import com.shop.dto.ShortBlogDto;
import com.shop.dto.comment.FirmReviewCommentDto;
import com.shop.entity.Firm;
import com.shop.entity.FirmType;
import com.shop.service.BlogService;
import com.shop.service.FirmCommentService;
import com.shop.service.FirmService;
import com.shop.service.FirmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class FirmController {

    private final FirmTypeService firmTypeService;
    private final FirmService firmService;
    private final BlogService blogService;
    private final FirmCommentService firmCommentService;


    @Autowired
    public FirmController(
            FirmTypeService firmTypeService,
            FirmService firmService,
            BlogService blogService,
            FirmCommentService firmCommentService
    ) {
        this.firmTypeService = firmTypeService;
        this.firmService = firmService;
        this.blogService = blogService;
        this.firmCommentService = firmCommentService;
    }


    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);


    @GetMapping("/1")
    public String getProductsByCategor1() {


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

        return "index";
    }


    @GetMapping("firm/{id}")
    public String getFirmById(@PathVariable Long id, Model model) {

        Firm firm = firmService.getById(id);
        model.addAttribute("firm", firm);

        return "product-details";
    }


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password) {


        return "login";
    }


    @GetMapping("product/product/{id}")
    public String getProductById1(@PathVariable Long id, Model model) {


        Pageable page = PageRequest.of(0,8);
        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);


        model.addAttribute("commentDto", new FirmReviewCommentDto());

        return "product-details";
    }

    @GetMapping("categories")
    public String getCategories(Model model) {
        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        return "categories";
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


    @GetMapping("search")
    public String searchProduct(Model model, @RequestParam(value = "name") String name) {
        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);


        return "product-search";
    }


    @PostMapping("product/{id}")
    public RedirectView comment(
            @ModelAttribute("review") FirmReviewCommentDto firmReviewCommentDto,
            @PathVariable Long id) {
        firmCommentService.saveComment(firmReviewCommentDto, id);

        return new RedirectView("product/" + id);
    }


    @GetMapping("basket")
    public String checkBasket(@CookieValue(name = "basket", required = false) String basket, Model model) {

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);


        return "basket";
    }
}
