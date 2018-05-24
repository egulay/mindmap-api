package com.mindmap.api.repository;

import com.mindmap.api.model.MindMap;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MindMapRepository extends ReactiveMongoRepository<MindMap, String> {
}
