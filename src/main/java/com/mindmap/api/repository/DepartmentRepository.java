package com.mindmap.api.repository;

import com.mindmap.api.model.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DepartmentRepository extends ReactiveMongoRepository<Department, String> {
}
