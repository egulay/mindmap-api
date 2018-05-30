package com.mindmap.api.controller;

import com.mindmap.api.model.NewNode;
import com.mindmap.api.model.TreeOutput;
import com.mindmap.api.model.TreeStructure;
import com.mindmap.api.model.Winners;
import com.mindmap.api.service.TreeStructureService;
import com.mindmap.api.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        TreeStructure root = treeStructureService.findByLabelAndDepartmentId(new Constants().ROOT_LABEL, departmentId).block();

        List<String> treeNodes = new ArrayList<>();
        Flux<TreeStructure> results = treeStructureService.findAllByDepartmentId(departmentId);

        Comparator<TreeStructure> compareWinners = Comparator.comparing(TreeStructure::getVotes).reversed();
        List<Integer> uniqueVotes = results.toStream().sorted(compareWinners).map(TreeStructure::getVotes).distinct().collect(Collectors.toList());
        int allVotes = results.toStream().mapToInt(i -> i.getVotes()).sum();

        Winners winners = new Winners();
        winners.allWinners = new ArrayList<>();

        int checkAllThreshold = results.count().block().intValue()/2;
        int checkWinersSum = 0;

        checkWinersSum += getCheckWinersSum(results, uniqueVotes, winners, checkAllThreshold, 0, checkWinersSum, allVotes);
        if(uniqueVotes.size() > 1) {
            checkWinersSum += getCheckWinersSum(results, uniqueVotes, winners, checkAllThreshold, 1, checkWinersSum, allVotes);
        }
        if(uniqueVotes.size() > 2) {
            checkWinersSum += getCheckWinersSum(results, uniqueVotes, winners, checkAllThreshold, 2, checkWinersSum, allVotes);
        }
        if(uniqueVotes.size() > 3) {
            checkWinersSum += getCheckWinersSum(results, uniqueVotes, winners, checkAllThreshold, 3, checkWinersSum, allVotes);
        }
        if(uniqueVotes.size() > 4) {
            checkWinersSum += getCheckWinersSum(results, uniqueVotes, winners, checkAllThreshold, 4, checkWinersSum, allVotes);
        }

        createTree(root, treeNodes, "");

        TreeOutput result = TreeOutput.builder()
                .treeNodes(treeNodes)
                .departmentId(departmentId)
                .rootLabel(new Constants().ROOT_LABEL)
                .winners(winners)
                .build();

        return Mono.just(result);
    }

    private int getCheckWinersSum(Flux<TreeStructure> results, List<Integer> uniqueVotes, Winners winners, int checkAllThreshold, int id, int checkWinersSum, int allVotes) {
        List<String> listRes = results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(id)).map(TreeStructure::getLabel).collect(Collectors.toList());
        double votesCount = results.toStream().filter(f -> f.getVotes() == uniqueVotes.get(id)).mapToDouble(i -> i.getVotes()).sum();
        String votesPercentage;
        if(allVotes > 0) {
            votesPercentage = String.valueOf((double)Math.round(votesCount / allVotes * 100 * 100)/100).concat("%");
        } else {
            votesPercentage = null;
        }
        checkWinersSum += listRes.size();
        if(checkWinersSum >= checkAllThreshold){
            return checkWinersSum;
        }
        switch (id){
            case 0:
                winners.setPercentOne(votesPercentage);
                winners.setWinnersLvlOne(listRes);
                break;
            case 1:
                winners.setPercentTwo(votesPercentage);
                winners.setWinnersLvlTwo(listRes);
                break;
            case 2:
                winners.setPercentThree(votesPercentage);
                winners.setWinnersLvlThree(listRes);
                break;
            case 3:
                winners.setPercentFour(votesPercentage);
                winners.setWinnersLvlFour(listRes);
                break;
            case 4:
                winners.setPercentFive(votesPercentage);
                winners.setWinnersLvlFive(listRes);
                break;
            default:
                break;
        }
        if (listRes !=null) {
            winners.allWinners.addAll(listRes);
        }
        return checkWinersSum;
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
