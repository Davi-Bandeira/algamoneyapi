package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.ResourceCreatedEvent;
import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.PersonRepository;
import com.example.algamoney.api.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> list(){
        return personRepository.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> listPerson(@PathVariable(value="code") Long code){
        Optional<Person> person = personRepository.findById(code);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response){
        Person personSave = personRepository.save(person);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, personSave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
    }

    @DeleteMapping("{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long code){
        personRepository.deleteById(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Person> update(@PathVariable Long code, @Valid @RequestBody Person person){
        Person personSave = personService.update(code, person);
        return ResponseEntity.ok(personSave);
    }
}
