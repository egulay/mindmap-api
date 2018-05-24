package com.mindmap.api.controller;


import com.mindmap.api.model.Account;
import com.mindmap.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/user")
    Mono<Account> getActiveUser(Principal principal) throws Exception {
        return accountService.findByName(principal.getName());
    }

    @GetMapping("/api/user/list")
    Flux<Account> getAccountList() {
        return accountService.findAll();
    }

    @GetMapping("/api/user/{id}")
    Mono<Account> getUserById(@PathVariable String id) {
        return accountService.findById(id);
    }
}
