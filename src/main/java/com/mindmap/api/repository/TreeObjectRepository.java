package com.mindmap.api.repository;

import com.mindmap.api.model.tree.NodeData;
import com.mindmap.api.model.tree.TreeObject;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreeObjectRepository extends ReactiveMongoRepository<TreeObject, String> {
    Mono<TreeObject> findByDepartmentId(String departmentId);

}
