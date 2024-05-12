package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.service.ServiceTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final ServiceTypeService serviceTypeService;

    public IndexController(final ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("serviceTypes", serviceTypeService.getTop(3));
        return "index";
    }
}
