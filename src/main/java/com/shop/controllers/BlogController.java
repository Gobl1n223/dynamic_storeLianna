package com.shop.controllers;

import com.shop.dto.CategoryDto;
import com.shop.dto.DtoWithPages;
import com.shop.dto.FullBlogDto;
import com.shop.dto.ShortBlogDto;
import com.shop.dto.comment.BlogReviewCommentDto;
import com.shop.entity.BlogCategory;
import com.shop.service.BlogCategoryService;
import com.shop.service.BlogCommentService;
import com.shop.service.BlogService;
import com.shop.service.FirmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller

public class BlogController {

    private final FirmTypeService firmTypeService;
    private final BlogService blogService;
    private final BlogCommentService blogCommentService;
    private final BlogCategoryService blogCategoryService;


    @Autowired
    public BlogController(
            FirmTypeService firmTypeService,
            BlogService blogService,
            BlogCommentService blogCommentService,
            BlogCategoryService blogCategoryService
    ) {
        this.firmTypeService = firmTypeService;
        this.blogService = blogService;
        this.blogCommentService = blogCommentService;
        this.blogCategoryService = blogCategoryService;
    }


    @GetMapping("blog/{id}")
    public String getBlogDetails(Model model, @PathVariable Long id) {

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<BlogCategory> blogCategories = blogCategoryService.getAllCategories();
        model.addAttribute("blogCategories", blogCategories);

        FullBlogDto fullBlog = blogService.getFullBlog(id);
        model.addAttribute("fullBlog", fullBlog);

        List<ShortBlogDto> blogsLimit = blogService.getBlogsByCategoryWithLimit(fullBlog.getBlogCategory().getNameCategory(), 3).stream()
                .filter(blog -> !blog.getId().equals(fullBlog.getId()))
                .collect(Collectors.toList());

        model.addAttribute("blogsLimit", blogsLimit);

        List<ShortBlogDto> newBlogs = blogService.getNewBlogs().stream()
                .filter(blog -> !blog.getId().equals(fullBlog.getId()))
                .collect(Collectors.toList());

        model.addAttribute("newBlogs", newBlogs);

        model.addAttribute("commentDto", new BlogReviewCommentDto());

        return "blog-details";
    }


    @GetMapping("blogs")
    public String getBlogDetails1(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") Integer p) {

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<BlogCategory> blogCategories = blogCategoryService.getAllCategories();
        model.addAttribute("blogCategories", blogCategories);

        List<ShortBlogDto> newBlogs = blogService.getNewBlogs();

        model.addAttribute("newBlogs", newBlogs);

        Pageable page2 = PageRequest.of(p-1,6, Sort.by("date"));

        DtoWithPages<List<ShortBlogDto>> blogs = blogService.getBlogs(page2);

        model.addAttribute("blogs", blogs.getDto());
        model.addAttribute("countPages", blogs.getPage() + 1);
        model.addAttribute("currentPage", page2.getPageNumber() + 1);

        return "blog-fullwidth";
    }


    @PostMapping("blog/{id}")
    public RedirectView createCommentInBlog(@ModelAttribute("review") BlogReviewCommentDto blogReviewCommentDto, @PathVariable Integer id) {
        blogCommentService.saveComment(blogReviewCommentDto, id);

        return new RedirectView("/blog/" + id);
    }


    @GetMapping("blogs/search/name")
    public String searchBlogsByName(
            @ModelAttribute("review") BlogReviewCommentDto blogReviewCommentDto,
            @RequestParam(value = "s") String name,
            Model model,
            @RequestParam(value = "p", required = false, defaultValue = "1") Integer p
    ) {
        model.addAttribute("search", name);

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<BlogCategory> blogCategories = blogCategoryService.getAllCategories();
        model.addAttribute("blogCategories", blogCategories);

        List<ShortBlogDto> newBlogs = blogService.getNewBlogs();

        model.addAttribute("newBlogs", newBlogs);

        Pageable page = PageRequest.of(p-1,6, Sort.by("date"));

        DtoWithPages<List<ShortBlogDto>> blogs = blogService.getBlogsByName(name, page);

        model.addAttribute("blogs", blogs.getDto());
        model.addAttribute("countPages", blogs.getPage() + 1);
        model.addAttribute("currentPage", page.getPageNumber() + 1);

        return "blog-search";
    }


    @GetMapping("blogs/search/category")
    public String searchBlogsByName1(
            @ModelAttribute("review") BlogReviewCommentDto blogReviewCommentDto,
            @RequestParam(value = "s") String category,
            Model model,
            @RequestParam(value = "p", required = false, defaultValue = "1") Integer p
    ) {
        model.addAttribute("search", category);

        List<CategoryDto> categories = firmTypeService.getCategories();
        model.addAttribute("categories", categories);

        List<BlogCategory> blogCategories = blogCategoryService.getAllCategories();
        model.addAttribute("blogCategories", blogCategories);

        List<ShortBlogDto> newBlogs = blogService.getNewBlogs();

        model.addAttribute("newBlogs", newBlogs);

        Pageable page = PageRequest.of(p-1,6, Sort.by("date"));

        DtoWithPages<List<ShortBlogDto>> blogs = blogService.getBlogsByCategory(category, page);

        model.addAttribute("blogs", blogs.getDto());
        model.addAttribute("countPages", blogs.getPage() + 1);
        model.addAttribute("currentPage", page.getPageNumber() + 1);

        return "blog-search";
    }
}
