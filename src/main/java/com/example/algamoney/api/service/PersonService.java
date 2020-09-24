package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;



    public Person update(Long code , Person person){
        Optional<Person> personSave = personRepository.findById(code);
        if(personSave.isPresent()){
            Person personUpdate = personSave.get();
            BeanUtils.copyProperties(person, personUpdate, "code");
            //return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(personSave.get()));
            return personRepository.save(personUpdate);
        }else{
            throw new EmptyResultDataAccessException(1);
        }
    }

}
