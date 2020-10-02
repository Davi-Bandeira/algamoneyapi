package com.example.algamoney.api.repository.Person;

import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.repository.filter.PersonFilter;
import com.example.algamoney.api.repository.projection.PersonResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryQuery {

    Page<Person> search(PersonFilter personFilter, Pageable pageable);
    Page<PersonResume> resume(PersonFilter personFilter, Pageable pageable);
}
