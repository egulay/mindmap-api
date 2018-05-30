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
public class Winners {
    private List<String> winnersLvlOne;
    private List<String> winnersLvlTwo;
    private List<String> winnersLvlThree;
    private List<String> winnersLvlFour;
    private List<String> winnersLvlFive;
}
