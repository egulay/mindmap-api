package com.mindmap.api;

import com.mindmap.api.model.Account;
import com.mindmap.api.model.Department;
import com.mindmap.api.model.Role;
import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.repository.AccountRepository;
import com.mindmap.api.repository.DepartmentRepository;
import com.mindmap.api.repository.RoleRepository;
import com.mindmap.api.repository.TreeStructureRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    private final TreeStructureRepository treeStructureRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(AccountRepository accountRepository
            , RoleRepository roleRepository, DepartmentRepository departmentRepository, TreeStructureRepository treeStructureRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.treeStructureRepository = treeStructureRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void seed() {
//        seedUserData();
        seedTreeData();
    }

    private void seedTreeData() {
        String nodeJsId = new ObjectId().toString();
        String javaId = new ObjectId().toString();
        String angularId = new ObjectId().toString();
        String materialId = new ObjectId().toString();
        String typeScriptId = new ObjectId().toString();
        String springId = new ObjectId().toString();
        String webFluxId = new ObjectId().toString();
        String mindMapId = new ObjectId().toString();

        String departmentId = "5b0be222f3be1b388cdc8dfd";

        treeStructureRepository.deleteAll()
                .then(
                        Mono.just("Mind Map").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(nodeJsId);
                                add(javaId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(mindMapId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Java").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(springId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(javaId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Spring MVC").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(webFluxId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(springId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Web Flux").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(webFluxId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("NodeJS").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(angularId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(nodeJsId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Angular CLI").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(typeScriptId);
                                add(materialId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(angularId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("TypeScript").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(typeScriptId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Material").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(materialId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                ).log().subscribe(value -> log.info("Completed: Seeding tree data..."));
    }


    private void seedUserData() {
        log.info("Seeding user data...");
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
                .subscribe(value -> log.info("Completed: Seeding user data...")
                );
    }
}
