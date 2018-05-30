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
public class TreeOutput {
    private List<String> treeNodes;
    private String departmentId;
    private String voteWinnerLabel;

    private Winners winners;
}
