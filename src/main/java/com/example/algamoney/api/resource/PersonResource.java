package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonRepository personRepository;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response){
        Person personSave = personRepository.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(personSave.getCode()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(personSave);
    }
}
