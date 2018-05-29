package com.mindmap.api.service;

import com.mindmap.api.model.tree.TreeObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreeObjectService {
    Flux<TreeObject> findAll();

    Mono<TreeObject> findByDepartmentId(String departmentId);

}
