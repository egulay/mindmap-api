package com.mindmap.api.service;

import com.mindmap.api.model.Tree;
import com.mindmap.api.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;

    @Autowired
    public TreeServiceImpl(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    @Override
    public Flux<Tree> findByDepartmentId(String departmentId) {
        return treeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Mono<Tree> findByLabel(String label) {
        return treeRepository.findByLabel(label);
    }

    @Override
    public Flux<Tree> findByParentId(String parentId) {
        return treeRepository.findByParentId(parentId);
    }
}
