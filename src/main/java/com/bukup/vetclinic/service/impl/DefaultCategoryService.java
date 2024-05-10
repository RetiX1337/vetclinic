package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Category;
import com.bukup.vetclinic.repository.CategoryRepository;
import com.bukup.vetclinic.service.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    public DefaultCategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        checkIfCategoryExistsByType(category.getType());
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category update(Category category) {
        final long id = category.getId();
        if (categoryRepository.existsById(id)) {
            return categoryRepository.save(category);
        }
        throw new EntityNotFoundException("Category with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(findById(id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    private void checkIfCategoryExistsByType(final String type) {
        if (categoryRepository.existsByType(type)) {
            throw new EntityExistsException("Category with type " + type + " already exists");
        }
    }
}
