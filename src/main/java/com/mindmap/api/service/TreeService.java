package com.mindmap.api.service;

import com.mindmap.api.model.Tree;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreeService {
    Flux<Tree> findByDepartmentId(String departmentId);
    Mono<Tree> findByLabel(String label);
    Flux<Tree> findByParentId(String parentId);
}
