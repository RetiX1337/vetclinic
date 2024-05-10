package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;

import java.util.List;

public interface UserService {
    User create(User role);

    Visitor createVisitorUser(Visitor visitor);

    Employee createEmployeeUser(Employee employee);

    User findById(long id);

    User findUserByEmail(final String email);

    User update(User user);

    void delete(long id);

    List<User> getAll();
}
