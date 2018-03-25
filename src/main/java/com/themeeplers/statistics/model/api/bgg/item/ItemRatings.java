package com.themeeplers.statistics.model.api.bgg.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ratings")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ItemRatings {

    @JacksonXmlProperty(isAttribute = false, localName = "averageweight")
    private ItemAverageWeight averageWeight;
    @JacksonXmlProperty(isAttribute = false, localName = "average")
    private ItemAverageRating averageRating;

}
