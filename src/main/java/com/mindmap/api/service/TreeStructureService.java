package com.mindmap.api.service;

import com.mindmap.api.model.TreeStructure;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TreeStructureService {
    Flux<TreeStructure> findAllByChildrenIds(List<String> ids);

    Flux<TreeStructure> findAllByDepartmentId(String departmentId);

    Mono<TreeStructure> findById(String id);

    Mono<TreeStructure> findByLabelAndDepartmentId(String label, String departmentId);

    Mono<TreeStructure> save(TreeStructure record);
}
