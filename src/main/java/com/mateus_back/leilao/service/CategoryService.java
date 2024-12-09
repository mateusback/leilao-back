package com.mateus_back.leilao.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.mateus_back.leilao.common.ActionResult;
import com.mateus_back.leilao.model.entities.Category;
import com.mateus_back.leilao.model.entities.Person;
import com.mateus_back.leilao.model.request.AddCategoryRequest;
import com.mateus_back.leilao.repository.interfaces.ICategoryRepository;
import com.mateus_back.leilao.repository.interfaces.IPersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IPersonRepository personRepository;

    public CategoryService(ICategoryRepository categoryRepository,
                           IPersonRepository personRepository) {
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }

    public ResponseEntity<ActionResult> create(AddCategoryRequest category) {
        return ActionResult.returnSuccess("Categoria criada com sucesso!", categoryRepository.save(ToEntity(category)));
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


    private Category ToEntity(AddCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setObservation(request.getObservation());
        personRepository.findbyId(request.getPersonId())
                .ifPresent(category::setPerson);
        return category;
    }
}