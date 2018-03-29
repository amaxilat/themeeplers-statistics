package com.themeeplers.statistics.model.api.meetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String created;
    private String description;
    @JsonProperty("local_date")
    private String localDate;
}
