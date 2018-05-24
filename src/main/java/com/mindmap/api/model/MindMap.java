package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@Builder
public class MindMap {
    @Id
    public String id;
    public String parentId;
    public String label;
    public List<Department> departments;
    public Vote vote;
}
