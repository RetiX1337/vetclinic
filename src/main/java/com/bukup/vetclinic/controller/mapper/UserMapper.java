package com.bukup.vetclinic.controller.mapper;

import com.bukup.vetclinic.dto.UserRequest;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User mapRequestToUser(final UserRequest userRequest) {
        User user = mapUser(userRequest, new User());
        user.setRoles(new HashSet<>(Set.of(roleService.readByName("USER"))));
        return user;
    }

    public User mapRequestToUserUpdate(final UserRequest userRequest, final User user) {
        return mapUser(userRequest, user);
    }

    private User mapUser(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return user;
    }
}
