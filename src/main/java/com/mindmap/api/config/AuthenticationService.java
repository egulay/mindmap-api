package com.mindmap.api.config;

import com.mindmap.api.model.Account;
import com.mindmap.api.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username).block();

        if (account == null) {
            throw new UsernameNotFoundException(
                    "User ".concat(username).concat(" not found."));
        }

        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User not authorized.");
        }

        return account;
    }
}

