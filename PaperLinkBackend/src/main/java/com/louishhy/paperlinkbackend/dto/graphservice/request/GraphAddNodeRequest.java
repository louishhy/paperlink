package com.louishhy.paperlinkbackend.dto.graphservice.request;

import lombok.Data;

@Data
public class GraphAddNodeRequest {
    private long graphId;
    private long paperId;
    private String note;
}
