package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Category;
import com.example.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category update(Long code, Category category){
        Optional<Category> categorySave= categoryRepository.findById(code);
        if (categorySave.isPresent()){
            Category categoryupdate = categorySave.get();
            BeanUtils.copyProperties(category,categoryupdate, "code");
            return categoryRepository.save(categoryupdate);
        }else{
            throw new EmptyResultDataAccessException(1);
        }
    }
}
