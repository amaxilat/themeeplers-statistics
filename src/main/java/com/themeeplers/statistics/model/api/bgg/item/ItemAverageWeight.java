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
@XmlRootElement(name = "averageweight")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ItemAverageWeight {

    @JacksonXmlProperty(isAttribute = true, localName = "value")
    private Double value;

}
