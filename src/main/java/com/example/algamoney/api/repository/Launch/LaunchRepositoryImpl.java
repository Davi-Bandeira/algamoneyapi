package com.example.algamoney.api.repository.Launch;

import com.example.algamoney.api.model.Category_;
import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.model.Launch_;
import com.example.algamoney.api.model.Person_;
import com.example.algamoney.api.repository.filter.LaunchFilter;
import com.example.algamoney.api.repository.projection.LaunchResume;
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

public class LaunchRepositoryImpl implements LaunchRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Launch> search(LaunchFilter launchFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Launch> criteria = builder.createQuery(Launch.class);

        Root<Launch> root = criteria.from(Launch.class);

        //Criar as restrições
        Predicate[] predicates = createRestrictions(launchFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Launch> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(launchFilter));
    }

    @Override
    public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<LaunchResume> criteria = builder.createQuery(LaunchResume.class);
        Root<Launch> root = criteria.from(Launch.class);

        criteria.select(builder.construct(LaunchResume.class
                , root.get(Launch_.CODE), root.get(Launch_.DESCRIPTION)
                , root.get(Launch_.EXPIRATION_DATE), root.get(Launch_.PAYMENT_DATE)
                , root.get(Launch_.VALUE), root.get(Launch_.TYPE)
                , root.get(Launch_.CATEGORY).get(Category_.NAME)
                , root.get(Launch_.PERSON).get(Person_.NAME)));

        Predicate[] predicates = createRestrictions(launchFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<LaunchResume> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(launchFilter));
    }

    private Long total(LaunchFilter launchFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Launch> root = criteria.from(Launch.class);

        Predicate[] predicates = createRestrictions(launchFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);
    }

    private Predicate[] createRestrictions(LaunchFilter launchFilter, CriteriaBuilder builder, Root<Launch> root){

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(launchFilter.getDescription())){
            predicates.add(builder.like(
                    builder.lower(root.get("description")),"%" + launchFilter.getDescription().toLowerCase() + "%"));
        }

        if(launchFilter.getExpirationDateFrom() != null){
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("expirationDate"),launchFilter.getExpirationDateFrom()));
        }

        if(launchFilter.getGetExpirationDateTo() != null){
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("expirationDate"), launchFilter.getGetExpirationDateTo()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
