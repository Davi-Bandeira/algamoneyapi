package com.example.algamoney.api.repository;

import com.example.algamoney.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
