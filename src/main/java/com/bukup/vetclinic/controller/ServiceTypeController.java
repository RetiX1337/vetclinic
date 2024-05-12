package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.ServiceTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(final ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
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
}
