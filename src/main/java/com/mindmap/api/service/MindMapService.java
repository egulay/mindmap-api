package com.mindmap.api.service;

import com.mindmap.api.model.MindMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MindMapService {
    Mono<MindMap> findById(String id);

    Flux<MindMap> findAll();

    Mono<MindMap> save(MindMap mindMap);

    Mono<MindMap> update(MindMap mindMap);

    Mono<MindMap> updateVote(String id);
}
