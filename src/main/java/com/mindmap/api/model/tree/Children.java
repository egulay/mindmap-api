package com.mindmap.api.model.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Children {
    String label;
    String data;
    String expandedIcon;
    String collapsedIcon;
    List<Leaf> children;
    String icon;
}
