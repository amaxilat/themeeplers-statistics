package com.themeeplers.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneraStatsDto {
    private long totalCount;
    private GameDto topWeight;
    private GameDto topRating;
    private GameDto topPlays;
}