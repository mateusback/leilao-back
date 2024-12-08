package com.mateus_back.leilao.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Category;
import com.mateus_back.leilao.repository.interfaces.ICategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<ActionResult> create(Category category) {
        return ActionResult.returnSuccess("Categoria criada com sucesso!", categoryRepository.save(category));
    }

    public ResponseEntity<ActionResult> update(Category category) {
        Category categorySaved = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));
        categorySaved.setName(category.getName());
        return ActionResult.returnSuccess("Categoria criada com sucesso!", categoryRepository.save(categorySaved));
    }

    public void delete(Long id) {
        Category categorySaved = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Objeto não encontrado"));
        categoryRepository.delete(categorySaved);
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }
}