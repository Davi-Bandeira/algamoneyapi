package com.example.algamoney.api.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LaunchFilter {

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate getExpirationDateTo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpirationDateFrom() {
        return expirationDateFrom;
    }

    public void setExpirationDateFrom(LocalDate expirationDateFrom) {
        this.expirationDateFrom = expirationDateFrom;
    }

    public LocalDate getGetExpirationDateTo() {
        return getExpirationDateTo;
    }

    public void setGetExpirationDateTo(LocalDate getExpirationDateTo) {
        this.getExpirationDateTo = getExpirationDateTo;
    }
}
