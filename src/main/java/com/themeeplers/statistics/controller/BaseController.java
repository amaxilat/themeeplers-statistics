package com.themeeplers.statistics.controller;

import com.themeeplers.statistics.model.BGGGame;
import com.themeeplers.statistics.model.GamePlays;
import com.themeeplers.statistics.model.Item;
import com.themeeplers.statistics.model.Name;
import com.themeeplers.statistics.repository.BGGGameRepository;
import com.themeeplers.statistics.repository.GameEntryRepository;
import com.themeeplers.statistics.service.BGGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class BaseController {

    @Autowired
    BGGGameRepository bggGameRepository;
    @Autowired
    GameEntryRepository gameEntryRepository;
    @Autowired
    BGGService bggService;

    @ResponseBody
    @RequestMapping(value = "/api/plays", produces = "application/json")
    public Set<GamePlays> playsAPI() {

        Set<GamePlays> map = new TreeSet<>();
        for (String s : gameEntryRepository.findDistinctUrl()) {
            map.add(new GamePlays(gameEntryRepository.countByUrl(s), s));
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/games", produces = "application/json")
    public List<String> games() {
        return gameEntryRepository.findDistinctUrl();
    }

    @RequestMapping(value = "/plays")
    public String plays(Model model) {
        Map<String, BGGGame> gamesMap = new HashMap<>();
        model.addAttribute("totalcount", bggGameRepository.count());
        model.addAttribute("games", gamesMap);
        model.addAttribute("plays", playsAPI());
        for (String s : games()) {
            if (s.startsWith("https://boardgamegeek.com/boardgame/") || s.startsWith("https://boardgamegeek.com/boardgameexpansion/")) {
                long id = Long.parseLong(s.split("/")[4]);
                BGGGame item = bggGameRepository.findByBggId(id);
                if (item != null) {
                    gamesMap.put(s, item);
                } else {
                    try {
                        Item game = bggService.getGame(id).getItem();
                        if (game == null) continue;
                        BGGGame newItem = new BGGGame();
                        newItem.setBggId(id);
                        newItem.setImage(game.getImage());
                        newItem.setWeight(game.getStatistics().getRatings().getAverageWeight().getValue());
                        newItem.setRating(game.getStatistics().getRatings().getAverageRating().getValue());
                        for (Name name : game.getNames()) {
                            if (name.getType().equals("primary")) {
                                newItem.setName(name.getValue());
                            }
                        }
                        newItem = bggGameRepository.save(newItem);
                        gamesMap.put(s, newItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "plays";
    }
}
