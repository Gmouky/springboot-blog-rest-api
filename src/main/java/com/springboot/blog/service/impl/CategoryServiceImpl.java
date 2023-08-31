package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import com.sun.jdi.PrimitiveValue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCatedory = categoryRepository.save(category);

        return modelMapper.map(savedCatedory, CategoryDto.class);
    }
}