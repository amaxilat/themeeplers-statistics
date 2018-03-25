package com.themeeplers.statistics.model.api.bgg.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "item")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class Item {

    @JacksonXmlProperty(isAttribute = true, localName = "type")
    private String type;
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private String id;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(isAttribute = false, localName = "name")
    private Set<ItemName> names;

    @JacksonXmlProperty(isAttribute = false, localName = "image")
    private String image;

    @JacksonXmlProperty(isAttribute = false, localName = "statistics")
    private ItemStatistics statistics;
}
