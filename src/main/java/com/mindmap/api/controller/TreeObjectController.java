package com.mindmap.api.controller;

import com.mindmap.api.model.tree.TreeObject;
import com.mindmap.api.service.TreeObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TreeObjectController {
    private final TreeObjectService treeObjectService;

    @Autowired
    public TreeObjectController(TreeObjectService treeObjectService) {
        this.treeObjectService = treeObjectService;
    }

    @GetMapping("api/node/getTree/")
    Flux<TreeObject> getTreeItems() {
        return treeObjectService.findAll();
    }

    @GetMapping("api/node/getTree/{departmentId}")
    Mono<TreeObject> getTreeItems(@PathVariable String departmentId) {
        return treeObjectService.findByDepartmentId(departmentId);
    }
}
