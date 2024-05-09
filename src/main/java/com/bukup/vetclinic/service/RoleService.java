package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Role;

public interface RoleService {
    Role create(Role role);

    Role readById(long id);

    Role readByName(String name);
}
