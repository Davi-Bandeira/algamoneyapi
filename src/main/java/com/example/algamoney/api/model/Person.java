package com.example.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")

public class Person {
    private Long code;
    private String name;
    private Boolean status;
}
