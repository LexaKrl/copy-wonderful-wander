package com.technokratos.service.auth;

import com.technokratos.exception.UserByUsernameNotFoundException;
import com.technokratos.model.UserPrincipal;
import com.technokratos.repository.UserRepository;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    /*
    todo Настроить кэширование, чтобы при запросе на /profile/me не ходить всегда в бд
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadByUsername doing... {}", username);
        return new UserPrincipal(userMapper.accountToUserEntity(
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UserByUsernameNotFoundException(username))
        ));
    }
}
