package com.louishhy.paperlinkbackend.dto.graphservice;

import com.louishhy.paperlinkbackend.model.Graph;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class GraphDTO {
    private Long graphId;
    private Long createdUserId;
    private String graphName;
    private String graphDescription;
    private Instant createdAt;
    private Instant updatedAt;
    // Nodes and edges
    private List<GraphNodeDTO> nodes = new ArrayList<>();
    private List<GraphEdgeDTO> edges = new ArrayList<>();

    public static GraphDTO fromEntity(Graph graph) {
        GraphDTO dto = new GraphDTO();
        dto.setGraphId(graph.getId());
        dto.setCreatedUserId(graph.getUserId());
        dto.setGraphName(graph.getName());
        dto.setGraphDescription(graph.getDescription());
        dto.setCreatedAt(graph.getCreatedAt());
        dto.setUpdatedAt(graph.getUpdatedAt());
        return dto;
    }
}
