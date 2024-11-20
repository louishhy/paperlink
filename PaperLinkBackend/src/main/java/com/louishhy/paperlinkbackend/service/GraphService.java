package com.louishhy.paperlinkbackend.service;

import com.louishhy.paperlinkbackend.dto.graphservice.GraphDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphEdgeDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.GraphNodeDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.SimplifiedGraphDTO;
import com.louishhy.paperlinkbackend.dto.graphservice.request.*;
import com.louishhy.paperlinkbackend.exception.graphservice.DuplicateResourceException;
import com.louishhy.paperlinkbackend.exception.graphservice.ResourceNotFoundException;
import com.louishhy.paperlinkbackend.model.Graph;
import com.louishhy.paperlinkbackend.model.GraphEdge;
import com.louishhy.paperlinkbackend.model.GraphNode;
import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.repository.GraphEdgeRepository;
import com.louishhy.paperlinkbackend.repository.GraphNodeRepository;
import com.louishhy.paperlinkbackend.repository.GraphRepository;
import com.louishhy.paperlinkbackend.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GraphService {
    private final GraphRepository graphRepository;
    private final GraphEdgeRepository graphEdgeRepository;
    private final GraphNodeRepository graphNodeRepository;
    private final PaperRepository paperRepository;

    @Autowired
    public GraphService(GraphRepository graphRepository,
                        GraphEdgeRepository graphEdgeRepository,
                        GraphNodeRepository graphNodeRepository, PaperRepository paperRepository) {
        this.graphRepository = graphRepository;
        this.graphEdgeRepository = graphEdgeRepository;
        this.graphNodeRepository = graphNodeRepository;
        this.paperRepository = paperRepository;
    }

    public GraphDTO createGraph(CreateGraphRequest request) {
        Graph graph = new Graph();
        graph.setName(request.getGraphName());
        graph.setDescription(request.getGraphDescription());
        graph.setUserId(request.getUserId());
        Graph savedGraph = graphRepository.save(graph);
        return GraphDTO.fromEntity(savedGraph);
    }

    public void deleteGraph(DeleteGraphRequest request) {
        Graph graph = graphRepository.findById(request.getGraphId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Graph with id %d not found", request.getGraphId())));
        graphRepository.delete(graph);
    }

    public GraphDTO getGraph(long graphId) {
        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Graph with id %d not found", graphId)));
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

    public List<SimplifiedGraphDTO> getAllGraphs() {
        List<Graph> graphs = graphRepository.findAll();
        return graphs.stream()
                .map(SimplifiedGraphDTO::fromEntity)
                .toList();
    }

    public GraphNodeDTO addGraphNode(GraphAddNodeRequest request) {
        Graph graph = graphRepository.findById(request.getGraphId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Graph with id %d not found", request.getGraphId())));
        Paper paper = paperRepository.findById(request.getPaperId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Paper with id %d not found", request.getPaperId())));
        // Check if node already exists
        boolean exists = graphNodeRepository.existsByGraphIdAndPaperId(request.getGraphId(), request.getPaperId());
        if (exists) {
            throw new DuplicateResourceException(
                    String.format("Node with graph id %d and paper id %d already exists", request.getGraphId(), request.getPaperId())
            );
        }

        GraphNode graphNode = new GraphNode();
        graphNode.setGraph(graph);
        graphNode.setPaper(paper);
        graphNode.setNote(request.getNote());
        GraphNode savedGraphNode = graphNodeRepository.save(graphNode);
        return GraphNodeDTO.fromEntity(savedGraphNode);
    }

    public GraphEdgeDTO addGraphEdge(GraphAddEdgeRequest request) {
        Graph graph = graphRepository.findById(request.getGraphId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Graph with id %d not found", request.getGraphId())));
        Paper citingPaper = paperRepository.findById(request.getCitingPaperId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Paper with id %d not found", request.getCitingPaperId())));
        Paper citedPaper = paperRepository.findById(request.getCitedPaperId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Paper with id %d not found", request.getCitedPaperId())));
        boolean exists = graphEdgeRepository.existsByGraphIdAndCitingPaperIdAndCitedPaperId(
                request.getGraphId(), request.getCitingPaperId(), request.getCitedPaperId());
        if (exists) {
            throw new DuplicateResourceException(
                    String.format("Edge with graph id %d, citing paper id %d and cited paper id %d already exists",
                            request.getGraphId(), request.getCitingPaperId(), request.getCitedPaperId())
            );
        }
        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setGraph(graph);
        graphEdge.setCitingPaper(citingPaper);
        graphEdge.setCitedPaper(citedPaper);
        graphEdge.setNote(request.getNote());
        GraphEdge savedGraphEdge = graphEdgeRepository.save(graphEdge);
        return GraphEdgeDTO.fromEntity(savedGraphEdge);
    }

    public void deleteGraphNode(GraphDeleteNodeRequest request) {
        GraphNode graphNode = graphNodeRepository.findById(request.getNodeId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Node with id %d not found", request.getNodeId())));
        graphNodeRepository.delete(graphNode);
    }

    public void deleteGraphEdge(GraphDeleteEdgeRequest request) {
        GraphEdge graphEdge = graphEdgeRepository.findById(request.getEdgeId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Edge with id %d not found", request.getEdgeId())));
        graphEdgeRepository.delete(graphEdge);
    }

}
