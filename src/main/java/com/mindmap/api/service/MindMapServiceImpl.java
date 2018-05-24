package com.mindmap.api.service;

import com.mindmap.api.model.MindMap;
import com.mindmap.api.repository.MindMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MindMapServiceImpl implements MindMapService {

    private final MindMapRepository mindMapRepository;

    @Autowired
    public MindMapServiceImpl(MindMapRepository mindMapRepository) {
        this.mindMapRepository = mindMapRepository;
    }

    @Override
    public Mono<MindMap> findById(String id) {
        return mindMapRepository.findById(id);
    }

    @Override
    public Flux<MindMap> findAll() {
        return mindMapRepository.findAll();
    }

    @Override
    public Mono<MindMap> save(MindMap mindMap) {
        return mindMapRepository.insert(mindMap);
    }

    @Override
    public Mono<MindMap> update(MindMap mindMap) {
        return mindMapRepository.save(mindMap);
    }

    @Override
    public Mono<MindMap> updateVote(String id) {
        MindMap mindMap = mindMapRepository.findById(id).block();
        mindMap.vote.setCount(mindMap.vote.getCount() + 1);
        return mindMapRepository.save(mindMap);
    }
}
