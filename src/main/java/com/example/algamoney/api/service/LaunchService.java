package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.LaunchRepository;
import com.example.algamoney.api.repository.PersonRepository;
import com.example.algamoney.api.service.exception.PersonNonExistentOrInactiveException;
import org.springframework.beans.BeanUtils;
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

        validatePerson(launch);
        return launchRepository.save(launch);

    }

    public Launch update(Long code , Launch launch){
        Launch launchSave = searchLaunch(code);
        if(!launch.getPerson().equals(launchSave.getPerson())){
            validatePerson(launch);
        }

        BeanUtils.copyProperties(launch, launchSave, "code");
        return launchRepository.save(launch);
    }

    private Launch searchLaunch(Long code){
        Optional<Launch> launch = launchRepository.findById(code);
        Launch launchSave;
        if (!launch.isPresent()){
            throw new IllegalArgumentException();
        }else {
            launchSave = launch.get();
        }
        return launchSave;
    }

    private void validatePerson(Launch launch){
        Optional<Person> person;
        Person personSave = null;

        if(launch.getPerson().getCode() != null){
            person = personRepository.findById(launch.getPerson().getCode());
            if (person.isPresent()) {
                personSave = person.get();
            }
        }
        if (personSave == null || personSave.isInactive()){
            throw new PersonNonExistentOrInactiveException();
        }
    }
}
