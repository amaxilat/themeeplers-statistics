package com.themeeplers.statistics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GameNightDto implements Comparable<GameNightDto> {
    private Long date;
    private Set<GameDto> games;

    @Override
    public int compareTo(GameNightDto o) {
        return o.getDate().compareTo(getDate());
    }
}
