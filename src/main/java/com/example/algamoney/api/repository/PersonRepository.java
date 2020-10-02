package com.example.algamoney.api.repository;

import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.Person.PersonRepositoryQuery;
import com.example.algamoney.api.repository.filter.PersonFilter;
import com.example.algamoney.api.repository.projection.PersonResume;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryQuery {
}
