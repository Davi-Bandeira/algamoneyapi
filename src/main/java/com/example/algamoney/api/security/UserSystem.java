package com.example.algamoney.api.security;

import com.example.algamoney.api.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserSystem extends  org.springframework.security.core.userdetails.User{

    private static final Long serialVersionUID = 1L;

    private final User user;

    public UserSystem(User user, Collection<? extends GrantedAuthority> authorities){
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
