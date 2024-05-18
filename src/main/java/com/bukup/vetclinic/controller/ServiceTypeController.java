package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.ServiceTypeRequest;
import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.service.CategoryService;
import com.bukup.vetclinic.service.ServiceTypeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceTypeController {
    private static final int SEARCH_PAGE_SIZE = 9;
    private final ServiceTypeService serviceTypeService;
    private final CategoryService categoryService;

    public ServiceTypeController(final ServiceTypeService serviceTypeService, final CategoryService categoryService) {
        this.serviceTypeService = serviceTypeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String searchServices(@RequestParam(value = "serviceNamePart", required = false) String serviceNamePart,
                                 @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                                 @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        final Pageable pageable = PageRequest.of(page, SEARCH_PAGE_SIZE);
        Page<ServiceType> serviceTypes =
                serviceTypeService.getAllByNamePartAndCategory(serviceNamePart, categoryIds, pageable);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("serviceTypes", serviceTypes);
        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("serviceNamePart", serviceNamePart);

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
    public String createServiceType(@Valid @ModelAttribute("serviceType") ServiceTypeRequest serviceTypeRequest,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("serviceType", serviceTypeRequest);
            model.addAttribute("categories", categoryService.getAll());
            return "services/newServiceType";
        }
        final ServiceType serviceType = new ServiceType();
        serviceType.setName(serviceTypeRequest.getName());
        serviceType.setDescription(serviceTypeRequest.getDescription());
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
        serviceTypeRequest.setDescription(serviceType.getDescription());
        serviceTypeRequest.setCategoryId(serviceType.getId());
        model.addAttribute("serviceType", serviceTypeRequest);
        model.addAttribute("categories", categoryService.getAll());
        return "services/editServiceType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public String updateServiceType(@PathVariable Long id,
                                    @Valid @ModelAttribute("serviceType") ServiceTypeRequest serviceTypeRequest,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("serviceType", serviceTypeRequest);
            model.addAttribute("categories", categoryService.getAll());
            return "services/editServiceType";
        }
        final ServiceType serviceType = serviceTypeService.findById(id);
        serviceType.setName(serviceTypeRequest.getName());
        serviceType.setDescription(serviceTypeRequest.getDescription());
        serviceType.setCategory(categoryService.findById(serviceTypeRequest.getCategoryId()));
        serviceTypeService.update(serviceType);
        return "redirect:/admin-panel";
    }
}
