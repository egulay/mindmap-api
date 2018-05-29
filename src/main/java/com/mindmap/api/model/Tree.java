package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tree {
    @Id
    public String id;
    public String parentId;
    public String departmentId;
    public String label;
    public String data;
    public Boolean hasChildren;
    public Boolean isLeaf;
}
