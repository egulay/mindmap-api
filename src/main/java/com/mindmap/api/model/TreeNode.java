package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TreeNode {
    String label;
    String data;
    String icon;
    String expandedIcon;
    String collapsedIcon;
    List<TreeNode> children;
    Boolean leaf;
    TreeNode parent;
}
