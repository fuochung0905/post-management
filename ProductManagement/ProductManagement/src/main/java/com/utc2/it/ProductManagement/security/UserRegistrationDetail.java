package com.utc2.it.ProductManagement.security;

import com.utc2.it.ProductManagement.dto.UserDto;
import com.utc2.it.ProductManagement.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserRegistrationDetail implements UserDetails {
    private String username;
    private String password;
    private boolean isEnable;
    private List<GrantedAuthority>authorities;
    public UserRegistrationDetail(User user){
        this.username=user.getEmail();
        this.password=user.getPassword();
        this.isEnable=user.getIsEnable();
        this.authorities=Arrays.stream(user.getRole()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
