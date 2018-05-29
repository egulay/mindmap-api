package com.mindmap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TreeOutput {
    private List<String> treeNodes;
    private String depatrmentId;
    private String voteWinnerLabel;
}
