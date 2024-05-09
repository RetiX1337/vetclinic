package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.User;

import java.util.List;

public interface UserService {
    User create(User role);

    User createVisitorUser(User user);

    User findById(long id);

    User findUserByEmail(final String email);

    User update(User user);

    void delete(long id);

    List<User> getAll();
}
