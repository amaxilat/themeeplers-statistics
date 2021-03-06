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
public class GamePlaysDto implements Comparable<GamePlaysDto> {
    private Long count;
    private GameDto game;

    @Override
    public int compareTo(GamePlaysDto o) {
        int diff = o.getCount().compareTo(getCount());
        if (diff == 0) {
            return o.getGame().compareTo(getGame());
        }
        return diff;
    }
}
