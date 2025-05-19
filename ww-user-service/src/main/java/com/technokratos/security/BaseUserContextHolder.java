package com.technokratos.security;

import com.technokratos.model.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BaseUserContextHolder {

    public UserPrincipal getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found in security context");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) principal;
            return userDetails;

        }
        throw new UsernameNotFoundException("Unknown principal type: " + principal.getClass().getName());
    }
}
