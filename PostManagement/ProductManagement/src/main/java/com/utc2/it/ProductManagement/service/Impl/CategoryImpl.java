package com.utc2.it.ProductManagement.service.Impl;

import com.utc2.it.ProductManagement.dto.CategoryDto;
import com.utc2.it.ProductManagement.entity.Category;
import com.utc2.it.ProductManagement.exception.ResourceNotFoundException;
import com.utc2.it.ProductManagement.repository.CategoryRepository;
import com.utc2.it.ProductManagement.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto,Category.class);
        Category addCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(addCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException("category","categoryId",categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category updateCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(updateCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException("category","categoryId",categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("category","categoryId",categoryId));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=this.categoryRepository.findAll();
        categories.stream().map((cat)->this.modelMapper.map(categories,CategoryDto.class)).collect(Collectors.toList());
        return null;
    }
}
