package com.louishhy.paperlinkbackend.dto.graphservice.request;

import lombok.Data;

@Data
public class CreateGraphRequest {
    private Long userId;
    private String graphName;
    private String graphDescription;
}
