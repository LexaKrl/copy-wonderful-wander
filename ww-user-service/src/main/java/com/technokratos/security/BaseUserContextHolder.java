package com.technokratos.security;

import com.technokratos.model.UserEntity;
import com.technokratos.model.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BaseUserContextHolder {

    public UserEntity getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found in security context");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) principal;
            return userDetails.getUser();

        } else if (principal instanceof UserEntity) {
            return (UserEntity) principal;
        }
        throw new UsernameNotFoundException("Unknown principal type: " + principal.getClass().getName());
    }
}
