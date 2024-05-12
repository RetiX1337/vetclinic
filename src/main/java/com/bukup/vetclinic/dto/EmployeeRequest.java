package com.bukup.vetclinic.dto;

import com.bukup.vetclinic.model.Category;
import com.bukup.vetclinic.model.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EmployeeRequest {
    private UserRequest userRequest;
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
