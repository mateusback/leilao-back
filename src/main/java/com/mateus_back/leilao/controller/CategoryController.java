package com.mateus_back.leilao.controller;

import java.util.List;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Category;
import com.mateus_back.leilao.model.request.AddCategoryRequest;
import com.mateus_back.leilao.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ActionResult> create(@RequestBody AddCategoryRequest category) {
        return categoryService.create(category);
    }

    @PutMapping
    public ResponseEntity<ActionResult> update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @GetMapping
    public List<Category> listAll() {
        return categoryService.listAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }

    @GetMapping("/find")
    public String find(@PathParam("name") String name,
                       @PathParam("age") Integer age) {
        System.out.println(name + " " + age);
        return name + " " + age;
    }
}