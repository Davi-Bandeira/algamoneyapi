package com.example.algamoney.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private final HttpServletResponse response;
    private final Long code;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, Long code){
        super(source);
        this.response = response;
        this.code = code;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCode() {
        return code;
    }
}
