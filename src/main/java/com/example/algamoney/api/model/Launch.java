package com.example.algamoney.api.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "releases")
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private String description;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    private BigDecimal value;

    private String note;

    @Enumerated(EnumType.STRING)
    private TypeLaunch type;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category codeCategory;

    @ManyToOne
    @JoinColumn(name = "person_code")
    private Person personCode;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TypeLaunch getType() {
        return type;
    }

    public void setType(TypeLaunch type) {
        this.type = type;
    }

    public Category getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(Category codeCategory) {
        this.codeCategory = codeCategory;
    }

    public Person getPersonCode() {
        return personCode;
    }

    public void setPersonCode(Person personCode) {
        this.personCode = personCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return code.equals(launch.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
