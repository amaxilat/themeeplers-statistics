package com.themeeplers.statistics.controller;

import com.themeeplers.statistics.dto.GameDto;
import com.themeeplers.statistics.dto.GamePlaysDto;
import com.themeeplers.statistics.dto.GeneraStatsDto;
import com.themeeplers.statistics.model.db.BGGGame;
import com.themeeplers.statistics.service.BGGService;
import com.themeeplers.statistics.service.DBService;
import com.themeeplers.statistics.service.MeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BaseRestController {

    @Autowired
    BGGService bggService;
    @Autowired
    MeetupService meetupService;
    @Autowired
    DBService dbService;

    @GetMapping(value = "/api/stats/general", produces = "application/json")
    public GeneraStatsDto statsGeneral() {
        GeneraStatsDto.GeneraStatsDtoBuilder builder = GeneraStatsDto.builder();
        builder.totalCount(dbService.countGames());
        BGGGame d = dbService.mostHeavyGame();
        GameDto heavy = new GameDto(d.getBggId(), d.getName(), d.getImage(), d.getWeight(), d.getRating());
        builder.heavy(heavy);
        BGGGame r = dbService.mostRatingGame();
        GameDto rating = new GameDto(r.getBggId(), r.getName(), r.getImage(), r.getWeight(), r.getRating());
        builder.rating(rating);
        return builder.build();
    }


    @GetMapping(value = "/api/stats/plays", produces = "application/json")
    public Set<GamePlaysDto> statsPlays() {
        return dbService.playCounts2();
    }


}