package com.mindmap.api.repository;

import com.mindmap.api.model.TreeStructure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TreeStructureRepository extends ReactiveMongoRepository<TreeStructure, String> {
    Flux<TreeStructure> findAllByChildrenIds(List<String> ids);

    Flux<TreeStructure> findAllByDepartmentId(String departmentId);

    Mono<TreeStructure> findById(String id);

    Mono<TreeStructure> findByLabelAndDepartmentId(String label, String departmentId);
}
