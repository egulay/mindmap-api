package com.mindmap.api.controller;

import com.mindmap.api.model.NewNode;
import com.mindmap.api.model.TreeOutput;
import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.model.Winners;
import com.mindmap.api.service.TreeStructureService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TreeStructureController {
    private final TreeStructureService treeStructureService;

    @Autowired
    public TreeStructureController(TreeStructureService treeStructureService) {
        this.treeStructureService = treeStructureService;
    }

    @GetMapping("api/tree/{departmentId}")
    private Mono<TreeOutput> getTree(@PathVariable String departmentId) {
        TreeStructure root = treeStructureService.findByLabelAndDepartmentId("Culture of Innovation", departmentId).block();

        List<String> treeNodes = new ArrayList<>();
        Flux<TreeStructure> results = treeStructureService.findAllByDepartmentId(departmentId);

        Comparator<TreeStructure> comparator = Comparator.comparing(TreeStructure::getVotes);
        String winner = results.toStream().max(comparator).orElse(root).getLabel();

        Comparator<TreeStructure> compareWinners = Comparator.comparing(TreeStructure::getVotes).reversed();
        List<Integer> uniqueVotes = results.toStream().sorted(compareWinners).map(TreeStructure::getVotes).distinct().collect(Collectors.toList());

        Winners winners = new Winners();

        int checkAllThreshold = results.count().block().intValue()/2;
        int checkWinersSum;

        winners.setWinnersLvlOne(results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(0)).map(TreeStructure::getLabel).collect(Collectors.toList()));
        checkWinersSum = winners.getWinnersLvlOne().size();
        if(checkWinersSum >= checkAllThreshold){
            winners.setWinnersLvlOne(null);
        }
        winners.setWinnersLvlTwo(results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(1)).map(TreeStructure::getLabel).collect(Collectors.toList()));
        checkWinersSum += winners.getWinnersLvlTwo().size();
        if(checkWinersSum >= checkAllThreshold){
            winners.setWinnersLvlTwo(null);
        }
        winners.setWinnersLvlThree(results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(2)).map(TreeStructure::getLabel).collect(Collectors.toList()));
        checkWinersSum += winners.getWinnersLvlThree().size();
        if(checkWinersSum >= checkAllThreshold){
            winners.setWinnersLvlThree(null);
        }
        winners.setWinnersLvlFour(results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(3)).map(TreeStructure::getLabel).collect(Collectors.toList()));
        checkWinersSum += winners.getWinnersLvlFour().size();
        if(checkWinersSum >= checkAllThreshold){
            winners.setWinnersLvlFour(null);
        }
        winners.setWinnersLvlFive(results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(4)).map(TreeStructure::getLabel).collect(Collectors.toList()));
        checkWinersSum += winners.getWinnersLvlFive().size();
        if(checkWinersSum >= checkAllThreshold){
            winners.setWinnersLvlFive(null);
        }
        createTree(root, treeNodes, "");

        TreeOutput result = TreeOutput.builder()
                .treeNodes(treeNodes)
                .departmentId(departmentId)
                .voteWinnerLabel(winner)
                .winners(winners)
                .build();

        return Mono.just(result);
    }

    @PostMapping(path = "api/tree/add", consumes = "application/json", produces = "application/json")
    public Mono<TreeOutput> saveRecord(@RequestBody NewNode node) {
        TreeStructure newNode = TreeStructure.builder()
                .id(new ObjectId().toString())
                .departmentId(node.getDepartmentId())
                .childrenIds(null)
                .label(node.getLabel())
                .build();
        TreeStructure resultNewNode = this.treeStructureService.save(newNode).block();

        TreeStructure parent = this.treeStructureService.findByLabelAndDepartmentId(node.getParentLabel(), node.getDepartmentId()).block();
        if (parent.childrenIds == null) {
            parent.childrenIds = new ArrayList<>();
        }
        parent.childrenIds.add(resultNewNode.getId());
        this.treeStructureService.save(parent).block();

        return getTree(node.getDepartmentId());
    }

    @GetMapping("api/tree/vote/{departmentId}/{label}")
    public Mono<TreeOutput> vote(@PathVariable String departmentId, @PathVariable String label) {
        TreeStructure votedNode = this.treeStructureService.findByLabelAndDepartmentId(label, departmentId).block();
        votedNode.votes++;
        this.treeStructureService.save(votedNode).block();

        return getTree(departmentId);
    }

    @GetMapping("api/tree/checkLabelExist/{departmentId}/{label}")
    public Mono<Boolean> checkLabelExist(@PathVariable String label, @PathVariable String departmentId) {
        TreeStructure resultLabel = this.treeStructureService.findByLabelAndDepartmentId(label, departmentId).block();
        if (resultLabel == null){
            return Mono.just(Boolean.FALSE);
        }
        if (resultLabel.getLabel() != null && resultLabel.getLabel().equals(label)) {
            return Mono.just(Boolean.TRUE);
        } else {
            return Mono.just(Boolean.FALSE);
        }
    }

    private void createTree(TreeStructure parentElement, List<String> result, String partial) {
        partial = partial.concat(parentElement.label).concat("|");
        if (parentElement.childrenIds != null) {
            for (String children : parentElement.childrenIds) {
                TreeStructure childrenNode = treeStructureService.findById(children).block();
                createTree(childrenNode, result, partial);
            }

        } else {
            result.add(partial.substring(0, partial.length() - 1));
        }
    }
}
