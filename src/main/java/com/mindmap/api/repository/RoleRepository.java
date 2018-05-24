package com.mindmap.api.repository;

import com.mindmap.api.model.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {
}
