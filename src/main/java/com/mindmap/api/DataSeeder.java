package com.mindmap.api;

import com.mindmap.api.model.Account;
import com.mindmap.api.model.Department;
import com.mindmap.api.model.Role;
import com.mindmap.api.repository.AccountRepository;
import com.mindmap.api.repository.DepartmentRepository;
import com.mindmap.api.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(AccountRepository accountRepository
            , RoleRepository roleRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void seed() {
        seedSampleData();
    }

    private void seedSampleData() {
        log.info("Seeding sample data...");
        roleRepository.deleteAll()
                .thenMany(
                        Flux.just("User", "Admin", "SysAdmin")
                                .flatMap(label -> {
                                            String code;
                                            switch (label) {
                                                case ("Admin"):
                                                    code = "ROLE_ADMIN";
                                                    break;
                                                case ("SysAdmin"):
                                                    code = "ROLE_SYSADMIN";
                                                    break;
                                                default:
                                                    code = "ROLE_USER";
                                                    break;
                                            }
                                            return roleRepository.save(Role.builder()
                                                    .id(new ObjectId().toString())
                                                    .code(code)
                                                    .label(label)
                                                    .build());
                                        }
                                )
                )
                .thenMany(
                        departmentRepository.deleteAll().thenMany(
                                Flux.just("IT", "Marketing", "Sales")
                                        .flatMap(name -> departmentRepository.save(Department.builder()
                                                .id(new ObjectId().toString())
                                                .name(name)
                                                .build())
                                        )
                        )
                )
                .then(
                        accountRepository.deleteAll().and(
                                Mono.just("admin")
                                        .flatMap(
                                                name -> {
                                                    List<Role> roles = roleRepository.findAll().collectList().block();
                                                    List<Department> departments = departmentRepository.findAll().collectList().block();
                                                    return accountRepository.save(Account.builder()
                                                            .id(new ObjectId().toString())
                                                            .fullName("System Admin")
                                                            .username(name)
                                                            .active(true)
                                                            .password(passwordEncoder.encode("password"))
                                                            .roles(roles)
                                                            .departments(departments).build()
                                                    );
                                                }
                                        )
                        )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("Completed: Seeding sample data...")
                );
    }
}
