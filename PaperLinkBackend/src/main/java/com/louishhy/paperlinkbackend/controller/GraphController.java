package com.louishhy.paperlinkbackend.controller;

import com.louishhy.paperlinkbackend.dto.graphservice.GraphDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.SimplifiedGraphDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphEdgeDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphNodeDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.request.*;
import com.louishhy.paperlinkbackend.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/graph")
public class GraphController {
    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping
    public ResponseEntity<List<SimplifiedGraphDTO>> getAllGraphs() {
        List<SimplifiedGraphDTO> graphs = graphService.getAllGraphs();
        return ResponseEntity.ok().body(graphs);
    }

    @GetMapping("/{graphId}")
    public ResponseEntity<GraphDTO> getGraph(@PathVariable long graphId) {
        GraphDTO graph = graphService.getGraph(graphId);
        return ResponseEntity.ok().body(graph);
    }

    @PostMapping
    public ResponseEntity<GraphDTO> createGraph(@RequestBody CreateGraphRequest request) {
        GraphDTO graph = graphService.createGraph(request);
        return ResponseEntity.ok().body(graph);
    }

    @DeleteMapping("/{graphId}")
    public ResponseEntity<Void> deleteGraph(@RequestBody DeleteGraphRequest request) {
        graphService.deleteGraph(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/node")
    public ResponseEntity<GraphNodeDTO> addNode(@RequestBody GraphAddNodeRequest request) {
        GraphNodeDTO node = graphService.addGraphNode(request);
        return ResponseEntity.ok().body(node);
    }

    @DeleteMapping("/node")
    public ResponseEntity<Void> deleteNode(@RequestBody GraphDeleteNodeRequest request) {
        graphService.deleteGraphNode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edge")
    public ResponseEntity<GraphEdgeDTO> addEdge(@RequestBody GraphAddEdgeRequest request) {
        GraphEdgeDTO dto = graphService.addGraphEdge(request);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/edge")
    public ResponseEntity<Void> deleteEdge(@RequestBody GraphDeleteEdgeRequest request) {
        graphService.deleteGraphEdge(request);
        return ResponseEntity.ok().build();
    }
}
