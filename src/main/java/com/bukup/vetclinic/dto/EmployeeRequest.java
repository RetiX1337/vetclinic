package com.bukup.vetclinic.dto;

import com.bukup.vetclinic.model.Category;
import com.bukup.vetclinic.model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EmployeeRequest {
    @Valid
    @NotNull
    private UserRequest userRequest;
    @Valid
    @NotNull
    private ScheduleRequest scheduleRequest;
    private List<Long> categoryIds;

    public EmployeeRequest() {}

    public EmployeeRequest(Employee employee) {
        this.userRequest = new UserRequest(employee.getUser());
        this.scheduleRequest = new ScheduleRequest(employee.getSchedule());
        this.categoryIds = employee.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
}
