package com.mindmap.api.repository;

import com.mindmap.api.model.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveMongoRepository<Department, String> {
    Mono<Department> findByName(String Name);
}
