package com.utc2.it.ProductManagement.controller;

import com.utc2.it.ProductManagement.dto.ApiResponse;
import com.utc2.it.ProductManagement.dto.CategoryDto;
import com.utc2.it.ProductManagement.entity.Category;
import com.utc2.it.ProductManagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createCategoryDto= this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
        CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{cateId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("category is delete successfully",true)
        ,HttpStatus.OK);
    }
    //get
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer catId){
        CategoryDto categoryDto=this.categoryService.getCategory(catId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    //getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>>getAllCategory(){
        List<CategoryDto> categoryDtoList=this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }



}
