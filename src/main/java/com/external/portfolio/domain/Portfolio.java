package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Portfolio {
    private Long portfolioId;
    private Long userId;
    private String fintechUseNum;

}
