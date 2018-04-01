package com.themeeplers.statistics.service;

import com.themeeplers.statistics.dto.GamePlays;
import com.themeeplers.statistics.model.api.bgg.item.Item;
import com.themeeplers.statistics.model.api.bgg.item.ItemName;
import com.themeeplers.statistics.model.api.meetup.EventGames;
import com.themeeplers.statistics.model.db.BGGGame;
import com.themeeplers.statistics.model.db.GameEntry;
import com.themeeplers.statistics.repository.BGGGameRepository;
import com.themeeplers.statistics.repository.GameEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void add(EventGames eventGames) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (final String eventGame : eventGames.getEvents()) {
            try {
                long time = sdf.parse(eventGames.getDate()).getTime();
                if (gameEntryRepository.countByUrlAndDate(eventGame, time) == 0) {
                    gameEntryRepository.save(new GameEntry(null, time, eventGame));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Double mostHeavy() {
        return bggGameRepository.findTopOrderByWeightDesc();
    }

    public Double mostRating() {
        return bggGameRepository.findTopOrderByRatingDesc();
    }

    public SortedMap<Long, Set<String>> getGameNights() {
        final SortedMap<Long, Set<String>> events = new TreeMap<>((o1, o2) -> -o1.compareTo(o2));
        for (final Long aLong : gameEntryRepository.findDistinctDate()) {
            final Set<String> links = new HashSet<>();
            for (final GameEntry gameEntry : gameEntryRepository.findByDate(aLong)) {
                if (gameEntry.getUrl().startsWith("https://boardgamegeek.com/boardgame/") || gameEntry.getUrl().startsWith("https://boardgamegeek.com/boardgameexpansion/")) {
                    final long id = Long.parseLong(gameEntry.getUrl().split("/")[4]);
                    final BGGGame entry = bggGameRepository.findByBggId(id);
                    links.add(entry.getImage());
                }
            }
            events.put(aLong, links);
        }
        return events;
    }

    public Set<BGGGame> getGameNight(final long time) {
        final Set<BGGGame> games = new HashSet<>();
        for (final GameEntry gameEntry : gameEntryRepository.findByDate(time)) {
            if (gameEntry.getUrl().startsWith("https://boardgamegeek.com/boardgame/") || gameEntry.getUrl().startsWith("https://boardgamegeek.com/boardgameexpansion/")) {
                final long id = Long.parseLong(gameEntry.getUrl().split("/")[4]);
                games.add(bggGameRepository.findByBggId(id));
            }
        }
        return games;
    }
}
