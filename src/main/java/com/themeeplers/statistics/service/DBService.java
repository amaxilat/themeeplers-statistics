package com.themeeplers.statistics.service;

import com.themeeplers.statistics.dto.GamePlays;
import com.themeeplers.statistics.model.api.bgg.item.Item;
import com.themeeplers.statistics.model.api.bgg.item.ItemName;
import com.themeeplers.statistics.model.db.BGGGame;
import com.themeeplers.statistics.repository.BGGGameRepository;
import com.themeeplers.statistics.repository.GameEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class DBService {

    @Autowired
    BGGGameRepository bggGameRepository;
    @Autowired
    GameEntryRepository gameEntryRepository;

    @Autowired
    BGGService bggService;

    public Set<GamePlays> playCounts() {
        final Set<GamePlays> map = new TreeSet<>();
        for (final String s : gameEntryRepository.findDistinctUrl()) {
            map.add(new GamePlays(gameEntryRepository.countByUrl(s), s));
        }
        return map;
    }

    public List<String> games() {
        return gameEntryRepository.findDistinctUrl();
    }

    public Long countGames() {
        return bggGameRepository.count();
    }

    public BGGGame findByBggId(long id) {
        return bggGameRepository.findByBggId(id);
    }

    public BGGGame findInBggAndStore(long id) {
        final Item game = bggService.getGame(id).getItem();

        if (game == null) return null;

        final BGGGame newItem = new BGGGame();

        newItem.setBggId(id);
        newItem.setImage(game.getImage());
        newItem.setWeight(game.getStatistics().getRatings().getAverageWeight().getValue());
        newItem.setRating(game.getStatistics().getRatings().getAverageRating().getValue());
        for (final ItemName name : game.getNames()) {
            if (name.getType().equals("primary")) {
                newItem.setName(name.getValue());
            }
        }
        return bggGameRepository.save(newItem);
    }
}
