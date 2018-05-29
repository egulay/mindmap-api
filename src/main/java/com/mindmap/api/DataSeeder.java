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
        String timeId = new ObjectId().toString();
        String mindsetId = new ObjectId().toString();
        String environmentId = new ObjectId().toString();

        //Time
        String flexibilityId = new ObjectId().toString();
        String agileId = new ObjectId().toString();
        String focusId = new ObjectId().toString();
//        String TimeId = new ObjectId().toString();

        //Environment
        String creativeRoomsId = new ObjectId().toString();
        String prototypingId = new ObjectId().toString();
        String recruitmentId = new ObjectId().toString();

        //Mindset
        String driveId = new ObjectId().toString();
        String skillsetId = new ObjectId().toString();
        String curiosityId = new ObjectId().toString();
        String openmindedId = new ObjectId().toString();
        String emotionalId = new ObjectId().toString();
        String courageId = new ObjectId().toString();
        String outsideOfBoxId = new ObjectId().toString();
        String agile2Id = new ObjectId().toString();
        String takingRiskId = new ObjectId().toString();
        String ownershipId = new ObjectId().toString();
        String continuousId = new ObjectId().toString();
        String rotationsId = new ObjectId().toString();
        String trainingsId = new ObjectId().toString();

        //networking
        String networkingId = new ObjectId().toString();
        String expertId = new ObjectId().toString();
        String rotations2Id = new ObjectId().toString();
        String conferencesId = new ObjectId().toString();
        String trainings2Id = new ObjectId().toString();

        //Trust
        String trustId = new ObjectId().toString();
        String freedomId = new ObjectId().toString();
        String failuresId = new ObjectId().toString();
        String safetyId = new ObjectId().toString();
        String colleaguesId = new ObjectId().toString();
        String managementId = new ObjectId().toString();

        //Recognition
        String recognitionId = new ObjectId().toString();
        String leadingId = new ObjectId().toString();
        String benefiId = new ObjectId().toString();
        String successId = new ObjectId().toString();
        String rewardId = new ObjectId().toString();

        //Fun
        String funId = new ObjectId().toString();


        String mindMapId = new ObjectId().toString();

        String departmentId = "5b0be222f3be1b388cdc8dfd";

        treeStructureRepository.deleteAll()
                .then(
                        Mono.just("Culture of Innovation").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(timeId);
                                add(mindsetId);
                                add(environmentId);
                                add(networkingId);
                                add(trustId);
                                add(recognitionId);
                                add(funId);
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
                        Mono.just("Time").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(flexibilityId);
                                add(agileId);
                                add(focusId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(timeId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Flexibility").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(flexibilityId)
                                .childrenIds(null)/
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("W/out heavy processes / Agile").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(agileId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Focus on get things done").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(focusId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Mindset").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(driveId);
                                add(skillsetId);
                                add(curiosityId);
//                                add(emotionalId);
                                add(courageId);
                                add(openmindedId);
                                add(outsideOfBoxId);
                                add(agile2Id);
                                add(takingRiskId);
                                add(ownershipId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(mindsetId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Drive").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(driveId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Skillset").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(continuousId);
                                add(rotationsId);
                                add(trainingsId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(skillsetId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Continuous learning").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(continuousId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Job rotations").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(rotationsId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Trainings").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(trainingsId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Curiosity").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(curiosityId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Open-minded").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(openmindedId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
/*                .then(
                        Mono.just("Emotional safety").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(emotionalId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )*/
                .then(
                        Mono.just("Courage").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(courageId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Think outside the box").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(outsideOfBoxId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Agile").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(agile2Id)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Taking risk/challenges").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(takingRiskId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Ownership").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(ownershipId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Environment").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(creativeRoomsId);
                                add(prototypingId);
                                add(recruitmentId);

                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(environmentId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Creative Rooms").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(creativeRoomsId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Prototyping environment").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(prototypingId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("HR recruitment").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(recruitmentId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Networking+Transparency").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(expertId);
                                add(rotations2Id);
                                add(conferencesId);
                                add(trainings2Id);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(networkingId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Expert communities").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(expertId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Rotations").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(rotations2Id)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Conferences").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(conferencesId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Trainings").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(trainings2Id)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Trust").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(freedomId);
                                add(failuresId);
                                add(safetyId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(trustId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Freedom to try out").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(freedomId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Allow to learn from failures").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(failuresId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Emotional safety").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(colleaguesId);
                                add(managementId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(safetyId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Colleagues").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(colleaguesId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Management").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(managementId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Recognition").flatMap(label -> {
                            List<String> childIds = new ArrayList<String>() {{
                                add(leadingId);
                                add(benefiId);
                                add(successId);
                                add(rewardId);
                            }};
                            return treeStructureRepository.save(TreeStructure.builder()
                                    .id(recognitionId)
                                    .childrenIds(childIds)
                                    .label(label)
                                    .departmentId(departmentId)
                                    .build());
                        })
                )
                .then(
                        Mono.just("Leading examples").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(leadingId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Benefit (extra training etc.)").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(benefiId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Success stories/Fame").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(successId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Reward").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(rewardId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )
                .then(
                        Mono.just("Fun").flatMap(label -> treeStructureRepository.save(TreeStructure.builder()
                                .id(funId)
                                .childrenIds(null)
                                .label(label)
                                .departmentId(departmentId)
                                .build()))
                )

                .log().subscribe(value -> log.info("Completed: Seeding tree data..."));
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
