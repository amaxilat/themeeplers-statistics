package com.themeeplers.statistics.controller;

import com.themeeplers.statistics.model.api.meetup.EventGames;
import com.themeeplers.statistics.model.db.BGGGame;
import com.themeeplers.statistics.service.BGGService;
import com.themeeplers.statistics.service.DBService;
import com.themeeplers.statistics.service.MeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

    @Autowired
    BGGService bggService;
    @Autowired
    MeetupService meetupService;
    @Autowired
    DBService dbService;

    @GetMapping(value = "/")
    public String home() {
        return "redirect:/plays";
    }


    @GetMapping(value = "/plays")
    public String plays(Model model) {
        Map<String, BGGGame> gamesMap = new HashMap<>();
        model.addAttribute("totalcount", dbService.countGames());
        model.addAttribute("games", gamesMap);
        model.addAttribute("plays", dbService.playCounts());
        model.addAttribute("heavy", dbService.mostHeavy());
        model.addAttribute("rating", dbService.mostRating());
        for (String s : dbService.games()) {
            if (s.startsWith("https://boardgamegeek.com/boardgame/") || s.startsWith("https://boardgamegeek.com/boardgameexpansion/")) {
                long id = Long.parseLong(s.split("/")[4]);
                BGGGame item = dbService.findByBggId(id);
                if (item != null) {
                    gamesMap.put(s, item);
                } else {
                    try {
                        BGGGame g = dbService.findInBggAndStore(id);
                        if (g == null) continue;
                        gamesMap.put(s, g);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "plays";
    }

    @ResponseBody
    @GetMapping(value = "/getGames/{meetupId}")
    public EventGames plays(Model model, @PathVariable("meetupId") final long meetupId) {
        final EventGames eventGames = meetupService.parseEvent(meetupId);
        dbService.add(eventGames);
        return eventGames;
    }
}
