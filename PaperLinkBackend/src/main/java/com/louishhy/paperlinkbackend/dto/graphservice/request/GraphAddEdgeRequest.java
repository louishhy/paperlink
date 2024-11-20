package com.louishhy.paperlinkbackend.dto.graphservice.request;

import lombok.Data;

@Data
public class GraphAddEdgeRequest {
    private long graphId;
    private long citingPaperId;
    private long citedPaperId;
    private String note;
}
