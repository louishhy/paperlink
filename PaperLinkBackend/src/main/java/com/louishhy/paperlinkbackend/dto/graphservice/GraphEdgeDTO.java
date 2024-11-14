package com.louishhy.paperlinkbackend.dto.graphservice;

import com.louishhy.paperlinkbackend.model.GraphEdge;
import lombok.Data;

import java.time.Instant;

@Data
public class GraphEdgeDTO {
    private Long edgeId;
    private Long graphId;
    private Long sourceNodeId;
    private Long targetNodeId;
    private Instant createdAt;
    private String note;

    public static GraphEdgeDTO fromEntity(GraphEdge graphEdge) {
        GraphEdgeDTO graphEdgeDTO = new GraphEdgeDTO();
        graphEdgeDTO.setEdgeId(graphEdge.getId());
        graphEdgeDTO.setGraphId(graphEdge.getGraph().getId());
        graphEdgeDTO.setSourceNodeId(graphEdge.getCitingPaper().getId());
        graphEdgeDTO.setTargetNodeId(graphEdge.getCitedPaper().getId());
        graphEdgeDTO.setCreatedAt(graphEdge.getCreatedAt());
        graphEdgeDTO.setNote(graphEdge.getNote());
        return graphEdgeDTO;
    }
}
