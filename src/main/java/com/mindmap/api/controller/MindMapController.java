package com.mindmap.api.controller;

import com.mindmap.api.model.MindMap;
import com.mindmap.api.service.MindMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MindMapController {
    private final MindMapService mindMapService;

    @Autowired
    public MindMapController(MindMapService mindMapService) {
        this.mindMapService = mindMapService;
    }

    @GetMapping("/api/node/{id}")
    Mono<MindMap> getNodeById(@PathVariable String id) {
        return mindMapService.findById(id);
    }

    @GetMapping("/api/node/all")
    Flux<MindMap> getAllNodes() {
        return mindMapService.findAll();
    }

    @PostMapping("/api/node/add")
    Mono<MindMap> addNode(@RequestBody MindMap mindMap) {
        return mindMapService.save(mindMap);
    }

    @PostMapping("/api/node/update")
    Mono<MindMap> updateNode(@RequestBody MindMap mindMap) {
        return mindMapService.update(mindMap);
    }

    @GetMapping("/api/node/updateVote/{id}")
    Mono<MindMap> updateVote(@PathVariable String id) {
        return mindMapService.updateVote(id);
    }
}
