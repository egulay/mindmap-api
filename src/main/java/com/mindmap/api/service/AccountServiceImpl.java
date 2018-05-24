package com.mindmap.api.service;

import com.mindmap.api.model.Account;
import com.mindmap.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> findByName(String name) {
        return accountRepository.findAccountByUsername(name);
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }
}