package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@Builder
public class Vote {
    @Id
    public String id;
    public String mindMapId;
    public String departmentId;
    public int count;
}
