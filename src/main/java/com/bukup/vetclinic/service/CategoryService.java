package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);

    Category findById(long id);

    Category update(Category category);

    void delete(long id);

    List<Category> getAll();
}
