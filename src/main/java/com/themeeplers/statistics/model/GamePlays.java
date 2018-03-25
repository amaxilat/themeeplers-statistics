package com.themeeplers.statistics.model;

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
public class GamePlays implements Comparable<GamePlays> {
    private Long count;
    private String url;

    @Override
    public int compareTo(GamePlays o) {
        int diff = o.getCount().compareTo(getCount());
        if (diff == 0) {
            return o.getUrl().compareTo(getUrl());
        }
        return diff;
    }
}
