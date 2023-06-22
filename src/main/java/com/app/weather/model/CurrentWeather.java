package com.app.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    @JsonProperty("temp_c")
    private double tempC;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("condition")
    private Condition condition;

    @JsonProperty("feelslike_c")
    private double feelsLikeC;

}