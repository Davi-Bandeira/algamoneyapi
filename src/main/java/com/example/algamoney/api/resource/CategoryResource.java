package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.model.Category;
import com.example.algamoney.api.repository.CategoryRepository;
import com.example.algamoney.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Category> list(){
        return categoryRepository.findAll();
    }

    @CrossOrigin(maxAge = 10, origins = {"http://localhost:8000"})
    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Category> listCategory(@PathVariable(value="code") Long code){
        Optional<Category> category = categoryRepository.findById(code);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response){
        Category categorySave = categoryRepository.save(category);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, categorySave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long code){
        categoryRepository.deleteById(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Category> update(@PathVariable Long code, @Valid @RequestBody Category category){
        Category categorySave = categoryService.update(code, category);
        return ResponseEntity.ok(categorySave);
    }

}
