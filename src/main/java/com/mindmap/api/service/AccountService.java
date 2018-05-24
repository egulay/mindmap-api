package com.mindmap.api.service;

import com.mindmap.api.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> findById(String id);

    Mono<Account> findByName(String name);

    Flux<Account> findAll();
}
