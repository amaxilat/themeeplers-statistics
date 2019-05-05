package com.themeeplers.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameNightDto implements Comparable<GameNightDto> {
    private Long date;

    @Override
    public int compareTo(GameNightDto o) {
        return o.getDate().compareTo(getDate());
    }
}
