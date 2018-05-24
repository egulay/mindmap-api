package com.mindmap.api.repository;

import com.mindmap.api.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findAccountByUsername(String userName);
}
