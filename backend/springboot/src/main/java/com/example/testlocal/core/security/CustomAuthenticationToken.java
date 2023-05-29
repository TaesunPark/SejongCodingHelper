 package com.example.testlocal.core.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    // principle
    private String email;
    private String studentNumber;

    public CustomAuthenticationToken(String email, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.studentNumber = credentials;
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return this.studentNumber;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
