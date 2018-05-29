package com.mindmap.api.controller;

import com.mindmap.api.model.NewNode;
import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.service.TreeStructureService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(path = "api/tree/add", consumes = "application/json", produces = "application/json")
    public Mono<String> saveRecord(@RequestBody NewNode node) {
        TreeStructure newNode = TreeStructure.builder()
                .id(new ObjectId().toString())
                .departmentId(node.getDepartmentId())
                .childrenIds(null)
                .label(node.getLabel())
                .build();
        TreeStructure resultNewNode = this.treeStructureService.save(newNode).block();

        TreeStructure parent = this.treeStructureService.findByLabelAndDepartmentId(node.getParentLabel(), node.getDepartmentId()).block();
        parent.childrenIds.add(resultNewNode.getId());
        this.treeStructureService.save(parent).block();

        return Mono.just("OK");
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
