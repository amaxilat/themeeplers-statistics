package com.themeeplers.statistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.themeeplers.statistics.model.api.meetup.Comment;
import com.themeeplers.statistics.model.api.meetup.Event;
import com.themeeplers.statistics.model.api.meetup.EventGames;
import com.themeeplers.statistics.repository.GameEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MeetupService {

    private RestTemplate template = new RestTemplate();

    @Autowired
    GameEntryRepository gameEntryRepository;

    @Value("${app.meetupKey}")
    private String meetupKey;

    public Event getEvent(final long id) {
        return template.getForObject("https://api.meetup.com/themeeplers/events/" + id + "?key=" + meetupKey, Event.class);
    }

    public Comment[] getEventComments(final long id) {
        return template.getForObject("https://api.meetup.com/themeeplers/events/" + id + "/comments?key=" + meetupKey, Comment[].class);
    }

    public EventGames parseEvent(final long id) {
        Set<String> links = new HashSet<>();
        Event event = getEvent(id);
        links.addAll(parseEventDescription(id));
        links.addAll(parseEventComments(id));
        return new EventGames(links, event.getLocalDate());
    }

    private Collection<? extends String> parseEventComments(long id) {
        Set<String> links = new HashSet<>();
        try {
            final Comment[] comments = getEventComments(id);

            for (final Comment comment : comments) {
                final String commentText = comment.getComment();
                final String[] parts = commentText.split("\n");
                for (final String part : parts) {
                    if (part.contains("https")) {
                        final String link = part.substring(part.indexOf("(") + 1, part.indexOf(")"));
                        links.add(link);
                    }
                }
            }
        } catch (Exception e) {
        }
        return new HashSet<>();
    }

    private Collection<? extends String> parseEventDescription(long id) {
        Set<String> links = new HashSet<>();
        try {
            Event event = getEvent(id);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String[] pParts = event.getDescription().split("<a href=\"");
            for (String pPart : pParts) {
                if (pPart.contains("https")) {
                    String link = pPart.substring(0, pPart.indexOf("\""));
                    System.out.println(link);
                    links.add(link);
                }
            }
            return links;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

}
