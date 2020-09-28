package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.LaunchRepository;
import com.example.algamoney.api.repository.PersonRepository;
import com.example.algamoney.api.service.exception.PersonNonExistentOrInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaunchService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LaunchRepository launchRepository;

    public Launch save(Launch launch) {
        Optional<Person> person = personRepository.findById(launch.getPerson().getCode());
        Person personSave;

        if(person.isPresent()) {
            personSave = person.get();
            if (personSave.isInactive()) {
                throw new PersonNonExistentOrInactiveException();
            } else {
                return launchRepository.save(launch);
            }
        }else{
            throw new PersonNonExistentOrInactiveException();
        }
    }

}
