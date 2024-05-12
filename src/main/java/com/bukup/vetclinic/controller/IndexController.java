package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.security.model.UserDetailsImpl;
import com.bukup.vetclinic.service.ServiceTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
