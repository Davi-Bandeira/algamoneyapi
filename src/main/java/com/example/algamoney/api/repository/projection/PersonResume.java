package com.example.algamoney.api.repository.projection;

public class PersonResume {

    private Long code;
    private String name;
    private String address;
    private Boolean active;

    public PersonResume(Long code, String name, String address, Boolean active){
        this.code = code;
        this.name = name;
        this.address = address;
        this.active = active;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
