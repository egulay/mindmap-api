package com.mindmap.api.controller;

import com.mindmap.api.model.Tree;
import com.mindmap.api.model.TreeNode;
import com.mindmap.api.service.TreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
public class TreeController {
    private final TreeService treeService;

    private List<TreeNode> children = new ArrayList<>();
    private String expandedIcon = "fa-folder-open";
    private String collapsedIcon = "fa-folder";
    private String icon = "fa-file-word-o";

    @Autowired
    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("api/tree/{departmentId}")
    Mono<List<String>> getTree(@PathVariable String departmentId) {
        List<String> output = new ArrayList<>();

        List<Tree> nodes = treeService.findByDepartmentId(departmentId).collectList().block();
        nodes.forEach(node ->{
            output.addAll(getChildren(node));
        });

        return Mono.just(output);
    }

    private List<String> childrenOutput = new ArrayList<>();
    private String input = "";
    private List<String> getChildren(Tree node) {
        if (node.hasChildren) {
            for (Tree child : treeService.findByParentId(node.id).collectList().block()) {
                input = input.concat(node.label.concat("!").concat(child.label));
                getChildren(child);
            }
        }

        childrenOutput.add(input);
        input = "";
        return childrenOutput;
    }
}
