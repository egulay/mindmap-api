package com.mindmap.api.service;

import com.mindmap.api.model.tree.NodeData;
import com.mindmap.api.model.tree.TreeObject;
import com.mindmap.api.repository.TreeObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TreeObjectServiceImpl implements TreeObjectService {
    private final TreeObjectRepository treeObjectRepository;

    @Autowired
    public TreeObjectServiceImpl(TreeObjectRepository treeObjectRepository) {
        this.treeObjectRepository = treeObjectRepository;
    }

    @Override
    public Flux<TreeObject> findAll() {
        return treeObjectRepository.findAll();
    }

    @Override
    public Mono<TreeObject> findByDepartmentId(String departmentId) {
        return treeObjectRepository.findByDepartmentId(departmentId);
    }
}
