package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.service.CategoryService;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.ServiceTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-panel")
public class AdminController {
    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final ServiceTypeService serviceTypeService;

    public AdminController(final EmployeeService employeeService, final CategoryService categoryService,
                           final ServiceTypeService serviceTypeService) {
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.serviceTypeService = serviceTypeService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getAdminPanel(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("serviceTypes", serviceTypeService.getAll());
        return "admin-panel";
    }
}
