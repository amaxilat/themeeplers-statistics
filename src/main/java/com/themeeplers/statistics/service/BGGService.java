package com.themeeplers.statistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.themeeplers.statistics.model.ItemsList;
import com.themeeplers.statistics.repository.GameEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BGGService {

    private RestTemplate template = new RestTemplate();

    @Autowired
    GameEntryRepository gameEntryRepository;

    public ItemsList getGame(final long id) {
        XmlMapper mapper = new XmlMapper();
        return template.getForObject("https://www.boardgamegeek.com/xmlapi2/thing?stats=1&id=" + id, ItemsList.class);
    }

    public static void main(String[] args) {
        try {
            BGGService service = new BGGService();
            ItemsList game = service.getGame(178900);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(mapper.writeValueAsString(game.getItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
