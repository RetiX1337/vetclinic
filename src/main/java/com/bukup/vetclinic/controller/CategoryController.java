package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.CategoryRequest;
import com.bukup.vetclinic.model.Category;
import com.bukup.vetclinic.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "categories/category";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new CategoryRequest());
        return "categories/newCategory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createCategory(@Valid @ModelAttribute("category") CategoryRequest categoryRequest,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(result.getAllErrors());
            model.addAttribute("category", categoryRequest);
            return "categories/newCategory";
        }
        final Category category = new Category();
        category.setType(categoryRequest.getType());
        categoryService.create(category);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editCategory(@PathVariable Long id, Model model) {
        final Category category = categoryService.findById(id);
        final CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setType(category.getType());
        model.addAttribute("category", categoryRequest);
        return "categories/editCategory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @Valid @ModelAttribute("category") CategoryRequest categoryRequest,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", categoryRequest);
            return "categories/editCategory";
        }
        final Category category = categoryService.findById(id);
        category.setType(categoryRequest.getType());
        categoryService.update(category);
        return "redirect:/admin-panel";
    }
}
