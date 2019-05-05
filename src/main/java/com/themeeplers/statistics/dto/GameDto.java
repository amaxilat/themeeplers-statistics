package com.themeeplers.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto implements Comparable<GameDto> {
    private Long bggId;
    private String name;
    private String image;
    private Double weight;
    private Double rating;
    private Long plays;

    @Override
    public int compareTo(GameDto o) {
        return o.getBggId().compareTo(getBggId());
    }
}
