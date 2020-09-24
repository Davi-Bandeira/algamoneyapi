package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.model.Category;
import com.example.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> listCategory(@PathVariable(value="code") Long code){
        Optional<Category> category = categoryRepository.findById(code);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response){
        Category categorySave = categoryRepository.save(category);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, categorySave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
    }
}
