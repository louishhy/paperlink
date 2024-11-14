package com.louishhy.paperlinkbackend.service;

import com.louishhy.paperlinkbackend.dto.graphservice.GraphDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphEdgeDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphNodeDTO;
import com.louishhy.paperlinkbackend.exception.graphservice.GraphNotFoundException;
import com.louishhy.paperlinkbackend.model.Graph;
import com.louishhy.paperlinkbackend.model.GraphEdge;
import com.louishhy.paperlinkbackend.model.GraphNode;
import com.louishhy.paperlinkbackend.repository.GraphEdgeRepository;
import com.louishhy.paperlinkbackend.repository.GraphNodeRepository;
import com.louishhy.paperlinkbackend.repository.GraphRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class GraphService {
    private final GraphRepository graphRepository;
    private final GraphEdgeRepository graphEdgeRepository;
    private final GraphNodeRepository graphNodeRepository;

    @Autowired
    public GraphService(GraphRepository graphRepository,
                        GraphEdgeRepository graphEdgeRepository,
                        GraphNodeRepository graphNodeRepository) {
        this.graphRepository = graphRepository;
        this.graphEdgeRepository = graphEdgeRepository;
        this.graphNodeRepository = graphNodeRepository;
    }


    public GraphDTO getGraph(long graphId) {
        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(() -> new GraphNotFoundException(String.format("Graph with id %d not found", graphId)));
        // Prepare GraphDTO
        GraphDTO graphDTO = GraphDTO.fromEntity(graph);
        // Get nodes
        List<GraphNode> graphNodes = graphNodeRepository.findByGraphId(graphId);
        List<GraphNodeDTO> graphNodeDTOS = graphNodes.stream()
                .map(GraphNodeDTO::fromEntity)
                .toList();
        graphDTO.setNodes(graphNodeDTOS);
        // Get edges
        List<GraphEdge> graphEdges = graphEdgeRepository.findByGraphId(graphId);
        List<GraphEdgeDTO> graphEdgeDTOS = graphEdges.stream()
                .map(GraphEdgeDTO::fromEntity)
                .toList();
        graphDTO.setEdges(graphEdgeDTOS);
        return graphDTO;
    }
}
