package com.mindmap.api.config;

import com.mindmap.api.model.Account;
import com.mindmap.api.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username).block();

        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User not authorized.");
        }

        return new org.springframework.security.core.userdetails.User(account.getUsername()
                , account.getPassword()
                , account.getAuthorities());
    }
}

