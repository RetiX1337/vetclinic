package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.ServiceTypeRequest;
import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.service.CategoryService;
import com.bukup.vetclinic.service.ServiceTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;
    private final CategoryService categoryService;

    public ServiceTypeController(final ServiceTypeService serviceTypeService, final CategoryService categoryService) {
        this.serviceTypeService = serviceTypeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String searchServices(@RequestParam(value = "serviceNamePart", required = false) String serviceNamePart, Model model) {
        final List<ServiceType> serviceTypes;
        if (serviceNamePart == null) {
            serviceTypes = serviceTypeService.getAll();
        } else {
            serviceTypes = serviceTypeService.getAllByNamePart(serviceNamePart);
        }
        model.addAttribute("serviceTypes", serviceTypes);

        return "services/search";
    }

    @GetMapping("/{id}")
    public String getServiceType(@PathVariable Long id, Model model) {
        model.addAttribute("serviceType", serviceTypeService.findById(id));
        return "services/service";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newServiceType(Model model) {
        model.addAttribute("serviceType", new ServiceTypeRequest());
        model.addAttribute("categories", categoryService.getAll());
        return "services/newServiceType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteServiceType(@PathVariable Long id) {
        serviceTypeService.delete(id);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createServiceType(@ModelAttribute("serviceType") ServiceTypeRequest serviceTypeRequest) {
        final ServiceType serviceType = new ServiceType();
        serviceType.setName(serviceTypeRequest.getName());
        serviceType.setCategory(categoryService.findById(serviceTypeRequest.getCategoryId()));
        serviceTypeService.create(serviceType);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editServiceType(@PathVariable Long id, Model model) {
        final ServiceType serviceType = serviceTypeService.findById(id);
        final ServiceTypeRequest serviceTypeRequest = new ServiceTypeRequest();
        serviceTypeRequest.setName(serviceType.getName());
        serviceTypeRequest.setCategoryId(serviceType.getId());
        model.addAttribute("serviceType", serviceTypeRequest);
        model.addAttribute("categories", categoryService.getAll());
        return "services/editServiceType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public String updateServiceType(@PathVariable Long id,
                                    @ModelAttribute("serviceType") ServiceTypeRequest serviceTypeRequest) {
        final ServiceType serviceType = serviceTypeService.findById(id);
        serviceType.setName(serviceTypeRequest.getName());
        serviceType.setCategory(categoryService.findById(serviceTypeRequest.getCategoryId()));
        serviceTypeService.update(serviceType);
        return "redirect:/admin-panel";
    }
}
