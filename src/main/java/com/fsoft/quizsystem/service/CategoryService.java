package com.fsoft.quizsystem.service;

import com.fsoft.quizsystem.object.dto.mapper.CategoryMapper;
import com.fsoft.quizsystem.object.dto.request.CategoryRequest;
import com.fsoft.quizsystem.object.entity.Category;
import com.fsoft.quizsystem.object.exception.ResourceNotFoundException;
import com.fsoft.quizsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any category with id " + id));
    }

    public Category createCategory(CategoryRequest requestBody) {
        Category category = categoryMapper.categoryRequestToEntity(requestBody);

        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, CategoryRequest requestBody) {
        Category category = this.findCategoryById(id);
        categoryMapper.updateEntity(category, requestBody);

        return categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        Category category = this.findCategoryById(id);

        categoryRepository.delete(category);
    }

}