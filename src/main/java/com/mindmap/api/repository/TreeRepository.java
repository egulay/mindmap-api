package com.mindmap.api.repository;

import com.mindmap.api.model.Tree;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreeRepository extends ReactiveMongoRepository<Tree, String> {
    Flux<Tree> findByDepartmentId(String departmentId);
    Mono<Tree> findByLabel(String label);
    Flux<Tree> findByParentId(String parentId);
}
