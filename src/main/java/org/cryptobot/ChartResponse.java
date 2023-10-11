package org.cryptobot;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ChartResponse {
    private String url;
    private List<ChartEntity> chartEntities;
}
