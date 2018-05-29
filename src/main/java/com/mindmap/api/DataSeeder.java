package com.mindmap.api;

import com.mindmap.api.model.Account;
import com.mindmap.api.model.Department;
import com.mindmap.api.model.Role;
import com.mindmap.api.model.tree.Children;
import com.mindmap.api.model.tree.Leaf;
import com.mindmap.api.model.tree.NodeData;
import com.mindmap.api.model.tree.TreeObject;
import com.mindmap.api.repository.AccountRepository;
import com.mindmap.api.repository.DepartmentRepository;
import com.mindmap.api.repository.RoleRepository;
import com.mindmap.api.repository.TreeObjectRepository;
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
    private final TreeObjectRepository treeObjectRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(AccountRepository accountRepository
            , RoleRepository roleRepository, DepartmentRepository departmentRepository, TreeObjectRepository treeObjectRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.treeObjectRepository = treeObjectRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void seed() {
//        seedUserData();
        seedTreeData();
    }

    private void seedTreeData() {
        log.info("Seeding tree data...");
        String expandedIcon = "fa-folder-open";
        String collapsedIcon = "fa-folder";
        String icon = "fa-file-word-o";

        treeObjectRepository.deleteAll().then(
                Mono.just("5b0be222f3be1b388cdc8dfd").flatMap(departmentId -> {
                    List<Leaf> leafListAngular = new ArrayList<Leaf>() {{
                        add(Leaf.builder().data(new ObjectId().toString()).icon(icon).label("Material").build());
                        add(Leaf.builder().data(new ObjectId().toString()).icon(icon).label("TypeScript").build());
                    }};
                    List<Children> childListNodeJs = new ArrayList<Children>() {{
                        add(Children.builder()
                                .label("Angular")
                                .data(new ObjectId().toString())
                                .collapsedIcon(collapsedIcon)
                                .expandedIcon(expandedIcon)
                                .children(leafListAngular)
                                .build());
                    }};

                    List<Leaf> leafListSpringMVC = new ArrayList<Leaf>() {{
                        add(Leaf.builder().data(new ObjectId().toString()).icon(icon).label("WebFlux").build());
                    }};
                    List<Children> childListJava = new ArrayList<Children>() {{
                        add(Children.builder()
                                .label("Spring MVC")
                                .data(new ObjectId().toString())
                                .collapsedIcon(collapsedIcon)
                                .expandedIcon(expandedIcon)
                                .children(leafListSpringMVC)
                                .build());
                    }};

                    List<NodeData> nodeList = new ArrayList<>();

                    nodeList.add(NodeData.builder()
                            .children(childListNodeJs)
                            .data(new ObjectId().toString())
                            .label("NodeJS")
                            .collapsedIcon(collapsedIcon)
                            .expandedIcon(expandedIcon)
                            .build());

                    nodeList.add(NodeData.builder()
                            .children(childListJava)
                            .data(new ObjectId().toString())
                            .label("Java")
                            .collapsedIcon(collapsedIcon)
                            .expandedIcon(expandedIcon)
                            .build());

                    return treeObjectRepository.save(TreeObject.builder()
                            .departmentId(departmentId)
                            .data(nodeList)
                            .build());
                })
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
