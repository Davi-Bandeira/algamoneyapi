package com.example.algamoney.api.repository.Person;

import com.example.algamoney.api.model.Address_;
import com.example.algamoney.api.model.Person;
import com.example.algamoney.api.model.Person_;
import com.example.algamoney.api.repository.filter.PersonFilter;
import com.example.algamoney.api.repository.projection.PersonResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Person> search(PersonFilter personFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);

        Root<Person> root = criteria.from(Person.class);

        //Criar as restrições
        Predicate[] predicates = createRestrictions(personFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Person> query = manager.createQuery(criteria);
        addRestrictionsOfPage(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(personFilter));
    }


    @Override
    public Page<PersonResume> resume(PersonFilter personFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<PersonResume> criteria = builder.createQuery(PersonResume.class);
        Root<Person> root = criteria.from(Person.class);

        criteria.select(builder.construct(PersonResume.class
                , root.get(Person_.CODE), root.get(Person_.NAME)
                , root.get(Person_.ADDRESS), root.get(Person_.ACTIVE)));

        Predicate[] predicates = createRestrictions(personFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<PersonResume> query = manager.createQuery(criteria);
        addRestrictionsOfPage(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(personFilter));
    }

    private Long total(PersonFilter personFilter){
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Person> root = criteria.from(Person.class);

        Predicate[] predicates = createRestrictions(personFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private Predicate[] createRestrictions(PersonFilter personFilter, CriteriaBuilder builder, Root<Person> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(personFilter.getName())){
            predicates.add(builder.like(
                    builder.lower(root.get(Person_.NAME)), "%" + personFilter.getName().toLowerCase() + "%"));
        }

        if(personFilter.getPlace() != null){
            predicates.add(builder.like(
                    builder.lower(root.get(Person_.ADDRESS).get(Address_.PLACE)), "%" + personFilter.getPlace().toLowerCase() + "%"));
        }

        if (personFilter.getActive() != null){
            predicates.add(builder.equal(
                    root.get(Person_.ACTIVE), personFilter.getActive()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addRestrictionsOfPage(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }
}
