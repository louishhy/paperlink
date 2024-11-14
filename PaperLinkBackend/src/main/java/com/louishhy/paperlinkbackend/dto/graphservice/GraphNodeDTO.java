package com.louishhy.paperlinkbackend.dto.graphservice;

import com.louishhy.paperlinkbackend.model.Graph;
import com.louishhy.paperlinkbackend.model.GraphNode;
import lombok.Data;

import java.time.Instant;

@Data
public class GraphNodeDTO {
    private Long nodeId;
    private Long graphId;
    private PaperDTO paper;
    private Instant createdAt;
    private String note;

    public static GraphNodeDTO fromEntity (GraphNode node) {
        GraphNodeDTO dto = new GraphNodeDTO();
        dto.setNodeId(node.getId());
        dto.setGraphId(node.getGraph().getId());
        dto.setPaper(PaperDTO.fromEntity(node.getPaper()));
        dto.setCreatedAt(node.getCreatedAt());
        dto.setNote(node.getNote());
        return dto;
    }
}
