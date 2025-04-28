package com.technokratos.service.auth;

import com.technokratos.exception.UserByUsernameNotFoundException;
import com.technokratos.model.UserPrincipal;
import com.technokratos.repository.UserRepository;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userMapper.accountToUserEntity(
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UserByUsernameNotFoundException(username))
        ));
    }
}
