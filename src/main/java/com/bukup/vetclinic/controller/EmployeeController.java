package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.controller.mapper.UserMapper;
import com.bukup.vetclinic.dto.EmployeeRequest;
import com.bukup.vetclinic.model.Category;
import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.service.CategoryService;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserMapper userMapper;

    public EmployeeController(final EmployeeService employeeService, final CategoryService categoryService,
                              final UserMapper userMapper, final UserService userService) {
        this.employeeService = employeeService;
        this.userMapper = userMapper;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("categories", categoryService.getAll());
        return "employees/newEmployee";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") EmployeeRequest employeeRequest,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(result.getAllErrors());
            model.addAttribute("employee", employeeRequest);
            model.addAttribute("categories", categoryService.getAll());
            return "employees/newEmployee";
        }
        final Employee employee = new Employee();
        final User user = userMapper.mapRequestToUser(employeeRequest.getUserRequest());
        employee.setUser(user);

        final Schedule schedule = mapEmployeeSchedule(employeeRequest);
        schedule.setEmployee(employee);
        employee.setSchedule(schedule);

        final Set<Category> categories = mapEmployeeCategories(employeeRequest);
        employee.setCategories(categories);

        userService.createEmployeeUser(employee);
        return "redirect:/admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editEmployee(@PathVariable Long id, Model model) {
        final Employee employee = employeeService.findById(id);
        final EmployeeRequest employeeRequest = new EmployeeRequest(employee);
        model.addAttribute("employee", employeeRequest);
        model.addAttribute("categories", categoryService.getAll());
        return "employees/editEmployee";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") EmployeeRequest employeeRequest,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(result.getAllErrors());
            model.addAttribute("employee", employeeRequest);
            model.addAttribute("categories", categoryService.getAll());
            return "employees/editEmployee";
        }
        final Employee employee = employeeService.findById(id);
        final User user = userMapper.mapRequestToUserUpdate(employeeRequest.getUserRequest(), employee.getUser());
        employee.setUser(user);

        final Schedule schedule = mapEmployeeScheduleUpdate(employeeRequest, employee);
        employee.setSchedule(schedule);

        final Set<Category> categories = mapEmployeeCategories(employeeRequest);
        employee.setCategories(categories);

        employeeService.update(employee);
        return "redirect:/admin-panel";
    }

    private Set<Category> mapEmployeeCategories(EmployeeRequest employeeRequest) {
        return employeeRequest.getCategoryIds().stream()
                .map(categoryService::findById)
                .collect(Collectors.toSet());
    }

    private static Schedule mapEmployeeSchedule(EmployeeRequest employeeRequest) {
        final Schedule schedule = new Schedule();
        schedule.setTimeSlotDuration(Duration.of(1, ChronoUnit.HOURS));
        schedule.setDayStartTime(employeeRequest.getScheduleRequest().getDayStartTime());
        schedule.setDayEndTime(employeeRequest.getScheduleRequest().getDayEndTime());
        return schedule;
    }

    private static Schedule mapEmployeeScheduleUpdate(EmployeeRequest employeeRequest, Employee employee) {
        final Schedule schedule = employee.getSchedule();
        schedule.setTimeSlotDuration(Duration.of(1, ChronoUnit.HOURS));
        schedule.setDayStartTime(employeeRequest.getScheduleRequest().getDayStartTime());
        schedule.setDayEndTime(employeeRequest.getScheduleRequest().getDayEndTime());
        return schedule;
    }
}
