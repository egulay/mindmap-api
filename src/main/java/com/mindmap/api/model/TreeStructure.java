package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TreeStructure {
    @Id
    public String id;
    public String departmentId;
    public List<String> childrenIds;
    public String label;
    @Builder.Default
    public int votes = 0;
}
