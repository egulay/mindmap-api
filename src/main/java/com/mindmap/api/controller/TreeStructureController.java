package com.mindmap.api.controller;

import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.service.TreeStructureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class TreeStructureController {
    private final TreeStructureService treeStructureService;

    @Autowired
    public TreeStructureController(TreeStructureService treeStructureService) {
        this.treeStructureService = treeStructureService;
    }

    @GetMapping("api/tree/{departmentId}")
    Mono<List<String>> getTree(@PathVariable String departmentId) {
        TreeStructure root = treeStructureService.findByLabelAndDepartmentId("Mind Map", departmentId).block();

        List<String> result = new ArrayList<>();

        createTree(root, result, "");

        return Mono.just(result);
    }

    private void createTree(TreeStructure parentElement, List<String> result, String partial) {
        partial = partial.concat(parentElement.label).concat("|");
        if (parentElement.childrenIds != null) {
            for (String children : parentElement.childrenIds) {
                TreeStructure childrenNode = treeStructureService.findById(children).block();
                createTree(childrenNode, result, partial);
            }

        } else {
            result.add(partial.substring(0, partial.length() - 1));
        }
    }


}
