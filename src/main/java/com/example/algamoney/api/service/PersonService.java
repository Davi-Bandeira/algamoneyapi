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
        Person personUpdate = findByCode(code);
        BeanUtils.copyProperties(person, personUpdate, "code");
        return personRepository.save(personUpdate);
    }

    public void updatePropActive(Long code, Boolean active) {
        Person personUpdate = findByCode(code);
        personUpdate.setActive(active);
        personRepository.save(personUpdate);
    }

    public Person findByCode(Long code){
        Optional<Person> personSave = personRepository.findById(code);
        if (!personSave.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }else{
            return personSave.get();
        }
    }
}
