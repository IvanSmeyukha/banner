package com.test_task.controller;

import com.test_task.entity.Category;
import com.test_task.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        category.setId(null);
        Optional<String> error = categoryService.saveCategory(category);
        return  error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        Optional<String> error = categoryService.saveCategory(category);
        return  error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        Optional<String> error = categoryService.deleteCategory(id);
        return  error
                .map(s -> new ResponseEntity<>(s, HttpStatus.CONFLICT))
                .orElseGet(() -> ResponseEntity.ok("OK"));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategoriesList(@RequestParam(name = "name", defaultValue = "") String name) {
        return new ResponseEntity<>(categoryService.getCategoriesList(name), HttpStatus.OK);
    }
}
