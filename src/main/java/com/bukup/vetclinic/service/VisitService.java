package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visit;

import java.util.List;

public interface VisitService {
    Visit create(Visit visit);

    Visit findById(long id);

    Visit update(Visit visit);

    void delete(long id);

    List<Visit> getAll();
}
