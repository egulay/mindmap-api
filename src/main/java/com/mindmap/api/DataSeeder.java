package com.mindmap.api;

import com.mindmap.api.model.*;
import com.mindmap.api.model.tree.Children;
import com.mindmap.api.model.tree.Leaf;
import com.mindmap.api.model.tree.NodeData;
import com.mindmap.api.model.tree.TreeObject;
import com.mindmap.api.repository.*;
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

    private final TreeStructureRepository  treeStructureRepository;

    private final PasswordEncoder passwordEncoder;

    private final TreeRepository treeRepository;

    @Autowired
    public DataSeeder(AccountRepository accountRepository
            , RoleRepository roleRepository, DepartmentRepository departmentRepository, TreeObjectRepository treeObjectRepository, TreeStructureRepository treeStructureRepository, PasswordEncoder passwordEncoder, TreeRepository treeRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.treeObjectRepository = treeObjectRepository;
        this.treeStructureRepository = treeStructureRepository;
        this.passwordEncoder = passwordEncoder;
        this.treeRepository = treeRepository;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void seed() {
//        seedUserData();
        seedTreeData3();
    }

    private void seedTreeData3() {
        String nodeJsId = new ObjectId().toString();
        String javaId = new ObjectId().toString();
        String angularId = new ObjectId().toString();
        String materialId = new ObjectId().toString();
        String typeScriptId = new ObjectId().toString();
        String springId = new ObjectId().toString();
        String webFluxId = new ObjectId().toString();
        String mindMapId= new ObjectId().toString();

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

    private void seedTreeData2() {
        log.info("Seeding tree data...");
//        String expandedIcon = "fa-folder-open";
//        String collapsedIcon = "fa-folder";
//        String icon = "fa-file-word-o";

        String departmentId = "5b0be222f3be1b388cdc8dfd";

        String rootId = "-";
        String nodeJsId = new ObjectId().toString();
        String javaId = new ObjectId().toString();
        String angularId = new ObjectId().toString();
        String materialId = new ObjectId().toString();
        String typeScriptId = new ObjectId().toString();
        String springId = new ObjectId().toString();
        String webFluxId = new ObjectId().toString();

        treeRepository.deleteAll()
                .then(
                        Mono.just("NodeJs")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(nodeJsId)
                                        .parentId(rootId)
                                        .label(label)
                                        .departmentId(departmentId)
                                        .isLeaf(false)
                                        .hasChildren(true)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("Java")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(javaId)
                                        .parentId(rootId)
                                        .label(label)
                                        .isLeaf(false)
                                        .hasChildren(true)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("Angular")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(angularId)
                                        .parentId(nodeJsId)
                                        .label(label)
                                        .isLeaf(false)
                                        .hasChildren(true)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("Spring MVC")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(springId)
                                        .parentId(javaId)
                                        .label(label)
                                        .isLeaf(false)
                                        .hasChildren(true)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("Material")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(materialId)
                                        .parentId(angularId)
                                        .label(label)
                                        .isLeaf(true)
                                        .hasChildren(false)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("TypeScript")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(typeScriptId)
                                        .parentId(angularId)
                                        .label(label)
                                        .isLeaf(true)
                                        .hasChildren(false)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                )
                .then(
                        Mono.just("WebFlux")
                                .flatMap(label -> treeRepository.save(Tree.builder()
                                        .id(webFluxId)
                                        .parentId(springId)
                                        .label(label)
                                        .isLeaf(true)
                                        .hasChildren(false)
                                        .departmentId(departmentId)
                                        .data("0").build()
                                ))
                ).log().subscribe(value -> log.info("Completed: Seeding tree data..."));
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
