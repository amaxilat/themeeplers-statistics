package com.themeeplers.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameDto implements Comparable<GameDto> {
    private Long bggId;
    private String name;
    private String image;
    private Double weight;
    private Double rating;

    @Override
    public int compareTo(GameDto o) {
        return o.getBggId().compareTo(getBggId());
    }
}
