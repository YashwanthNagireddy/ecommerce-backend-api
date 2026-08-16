package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.entity.Category;
import com.yashwanth.ecommerce.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    // =========================
    // CREATE CATEGORY
    // ADMIN ONLY
    // SecurityConfig handles authorization
    // =========================
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category
    ) {

        Category savedCategory =
                categoryRepository.save(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategory);
    }

    // =========================
    // GET ALL CATEGORIES
    // USER + ADMIN
    // =========================
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(
                categoryRepository.findAll()
        );
    }

    // =========================
    // GET CATEGORY BY ID
    // USER + ADMIN
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(
            @PathVariable Long id
    ) {

        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    // =========================
    // UPDATE CATEGORY
    // ADMIN ONLY
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ) {

        return categoryRepository.findById(id)
                .map(existingCategory -> {

                    existingCategory.setName(
                            category.getName()
                    );

                    existingCategory.setDescription(
                            category.getDescription()
                    );

                    Category updatedCategory =
                            categoryRepository.save(
                                    existingCategory
                            );

                    return ResponseEntity.ok(
                            updatedCategory
                    );
                })
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    // =========================
    // DELETE CATEGORY
    // ADMIN ONLY
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id
    ) {

        if (!categoryRepository.existsById(id)) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Category not found");
        }

        categoryRepository.deleteById(id);

        return ResponseEntity.ok(
                "Category deleted successfully"
        );
    }
}