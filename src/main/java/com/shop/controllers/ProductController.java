package com.shop.controllers;

import com.shop.dto.BasketProductDto;
import com.shop.dto.CategoryDto;
import com.shop.dto.ProductDto;
import com.shop.dto.ProductShortDto;
import com.shop.dto.ShortBlogDto;
import com.shop.dto.comment.ProductReviewCommentDto;
import com.shop.service.BlogService;
import com.shop.service.ProductCommentService;
import com.shop.service.ProductService;
import com.shop.service.ProductTypeService;
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

@Controller

public class ProductController {

    private final ProductTypeService productTypeService;
    private final ProductService productService;
    private final BlogService blogService;
    private final ProductCommentService productCommentService;


    @Autowired
    public ProductController(
            ProductTypeService productTypeService,
            ProductService productService,
            BlogService blogService,
            ProductCommentService productCommentService
    ) {
        this.productTypeService = productTypeService;
        this.productService = productService;
        this.blogService = blogService;
        this.productCommentService = productCommentService;
    }


    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);


    @GetMapping
    public String getProductsByCategory(@RequestParam(required = false,defaultValue = "0") int p, Model model) {
        Pageable page = PageRequest.of(p,8);

        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<ProductShortDto> productShortDtos0 = productTypeService.getShortByCategory("Футболки", page);
        model.addAttribute("productFirst", productShortDtos0);

        List<ProductShortDto> productShortDtos1 = productTypeService.getShortByCategory("Кружки", page);
        model.addAttribute("productSecond", productShortDtos1);

        List<ProductShortDto> products = productService.getProductByDiscount(page);
        model.addAttribute("productsDiscount", products);

        List<ShortBlogDto> blogsLimit = blogService.getBlogsWithLimit(6);
        model.addAttribute("blogsLimit", blogsLimit);

        return "index";
    }


    @GetMapping("product/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getById(id);
        model.addAttribute("productDetail", productDto);

        Pageable page = PageRequest.of(0,8);
        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<ProductShortDto> similarProducts = productTypeService.getShortByCategory(productDto.getCategory(), page);
        model.addAttribute("products", similarProducts);

        model.addAttribute("commentDto", new ProductReviewCommentDto());

        return "product-details";
    }


    @GetMapping("categories")
    public String getCategories(Model model) {
        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);

        return "categories";
    }


    @GetMapping("category/{category}")
    public String getProductsByCategory(Model model, @NotNull @PathVariable String category) {
        Pageable page = PageRequest.of(0,8);
        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);


        List<ProductShortDto> products = productTypeService.getShortByCategory(category, page);
        model.addAttribute("products", products);

        return "product-search";
    }


    @GetMapping("search")
    public String searchProduct(Model model, @RequestParam(value = "name") String name) {
        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<ProductShortDto> productsByName = productService.getProductsByName(name);
        model.addAttribute("products", productsByName);

        return "product-search";
    }


    @PostMapping("product/{id}")
    public RedirectView comment(
            @ModelAttribute("review") ProductReviewCommentDto productReviewCommentDto,
            @PathVariable Long id) {
        productCommentService.saveComment(productReviewCommentDto, id);

        return new RedirectView("product/" + id);
    }


    @GetMapping("basket")
    public String checkBasket(@CookieValue(name = "basket", required = false) String basket, Model model) {

        List<CategoryDto> categories = productTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<BasketProductDto> shortProducts = productService.getProductsForBasketByIds(basket);
        model.addAttribute("basketProducts", shortProducts);

        return "basket";
    }
}
