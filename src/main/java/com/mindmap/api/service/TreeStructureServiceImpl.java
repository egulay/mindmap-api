package com.mindmap.api.service;

import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.repository.TreeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TreeStructureServiceImpl implements TreeStructureService {
    private final TreeStructureRepository treeStructureRepository;

    @Autowired
    public TreeStructureServiceImpl(TreeStructureRepository treeStructureRepository) {
        this.treeStructureRepository = treeStructureRepository;
    }

    @Override
    public Flux<TreeStructure> findAllByChildrenIds(List<String> ids) {
        return treeStructureRepository.findAllByChildrenIds(ids);
    }

    @Override
    public Mono<TreeStructure> findById(String id) {
        return treeStructureRepository.findById(id);
    }

    @Override
    public Mono<TreeStructure> findByLabelAndDepartmentId(String label, String departmentId) {
        return treeStructureRepository.findByLabelAndDepartmentId(label, departmentId);
    }

}
