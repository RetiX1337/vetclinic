package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.UserService;
import com.bukup.vetclinic.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final VisitorService visitorService;

    public UserController(UserService userService, EmployeeService employeeService, VisitorService visitorService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.visitorService = visitorService;
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);

        if (employeeService.existsByUserId(id)) {
            Employee employee = employeeService.findById(id);
            model.addAttribute("employee", employee);
            model.addAttribute("categories", employee.getCategories());
            model.addAttribute("visits", employee.getVisits());
            return "profiles/employee";
        } else if (visitorService.existsByUserId(id)) {
            Visitor visitor = visitorService.findById(id);
            model.addAttribute("visitor", visitor);
            model.addAttribute("pets", visitor.getPets());
            model.addAttribute("visits", visitor.getVisits());
            return "profiles/visitor";
        } else {
            throw new IllegalArgumentException("User with id " + id + " is neither an employee nor a visitor");
        }
    }
}
