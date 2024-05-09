package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;

import java.util.List;

public interface VisitorService {
    Visitor create(Visitor visitor);

    Visitor findById(long id);

    Visitor update(Visitor visitor);

    void delete(long id);

    List<Visitor> getAll();
}
