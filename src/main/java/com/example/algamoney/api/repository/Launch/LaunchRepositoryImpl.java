package com.example.algamoney.api.repository.Launch;

import com.example.algamoney.api.model.Launch;
import com.example.algamoney.api.repository.filter.LaunchFilter;
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
    public List<Launch> search(LaunchFilter launchFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Launch> criteria = builder.createQuery(Launch.class);

        Root<Launch> root = criteria.from(Launch.class);

        //Criar as restrições
        Predicate[] predicates = createRestrictions(launchFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Launch> query = manager.createQuery(criteria);
        return query.getResultList();
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
