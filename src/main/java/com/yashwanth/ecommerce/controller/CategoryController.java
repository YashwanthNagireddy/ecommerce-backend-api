package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Category;
import com.yashwanth.ecommerce.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    private final CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // CREATE CATEGORY
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }


    // GET ALL CATEGORIES
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    // GET CATEGORY BY ID
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {

        return categoryRepository.findById(id)
                .orElse(null);
    }


    // UPDATE CATEGORY
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {


        Category existingCategory =
                categoryRepository.findById(id)
                        .orElse(null);


        if(existingCategory != null) {

            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());

            return categoryRepository.save(existingCategory);
        }


        return null;
    }


    // DELETE CATEGORY
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryRepository.deleteById(id);

        return "Category deleted successfully";
    }
}