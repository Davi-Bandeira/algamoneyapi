package com.example.algamoney.api.repository.projection;

import com.example.algamoney.api.model.TypeLaunch;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LaunchResume {

    private Long code;
    private String description;
    private LocalDate expirationDate;
    private LocalDate paymentDate;
    private BigDecimal value;
    private TypeLaunch type;
    private String category;
    private String person;

    public LaunchResume(Long code, String description, LocalDate expirationDate,
                        LocalDate paymentDate, BigDecimal value, TypeLaunch type,
                        String category, String person) {
        this.code = code;
        this.description = description;
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
        this.value = value;
        this.type = type;
        this.category = category;
        this.person = person;
    }

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

    public TypeLaunch getType() {
        return type;
    }

    public void setType(TypeLaunch type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
