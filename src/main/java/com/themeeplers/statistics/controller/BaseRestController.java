package com.themeeplers.statistics.controller;

import com.themeeplers.statistics.dto.GameDto;
import com.themeeplers.statistics.dto.GameNightDto;
import com.themeeplers.statistics.dto.GamePlayDto;
import com.themeeplers.statistics.dto.GamePlays;
import com.themeeplers.statistics.dto.GeneraStatsDto;
import com.themeeplers.statistics.model.api.meetup.EventGames;
import com.themeeplers.statistics.model.db.BGGGame;
import com.themeeplers.statistics.service.BGGService;
import com.themeeplers.statistics.service.DBService;
import com.themeeplers.statistics.service.MeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

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
        final GeneraStatsDto.GeneraStatsDtoBuilder builder = GeneraStatsDto.builder();
        builder.totalCount(dbService.countGames());
        final BGGGame d = dbService.mostHeavyGame();
        builder.topWeight(GameDto.builder().bggId(d.getBggId()).name(d.getName()).image(d.getImage()).weight(d.getWeight()).rating(d.getRating()).build());
        final BGGGame r = dbService.mostRatingGame();
        builder.topRating(GameDto.builder().bggId(r.getBggId()).name(r.getName()).image(r.getImage()).weight(r.getWeight()).rating(r.getRating()).build());
        final GamePlays topPlays = dbService.playCounts().iterator().next();
        final GameDto p = dbService.getGame(topPlays.getUrl());
        p.setPlays(topPlays.getCount());
        builder.topPlays(p);
        return builder.build();
    }

    @GetMapping(value = "/api/gamenight", produces = "application/json")
    public SortedSet<GameNightDto> gamenights() {
        return dbService.getGameNights2();
    }

    @GetMapping(value = "/api/gamenight/{time}", produces = "application/json")
    public GameNightDto gamenights(@PathVariable("time") final long time) {
        return dbService.getGameNights2().stream().filter(gameNightDto -> gameNightDto.getDate() == time).collect(Collectors.toSet()).iterator().next();
    }

    @GetMapping(value = "/api/stats/plays", produces = "application/json")
    public Set<GamePlayDto> statsPlays() {
        return dbService.playCounts2();
    }


    @ResponseBody
    @GetMapping(value = "/getGames/{meetupId}")
    public EventGames plays(Model model, @PathVariable("meetupId") final long meetupId) {
        final EventGames eventGames = meetupService.parseEvent(meetupId);
        dbService.add(eventGames);
        return eventGames;
    }
}