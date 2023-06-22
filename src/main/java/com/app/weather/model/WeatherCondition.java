package com.app.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherCondition {
    private double temperature;
    private int humidity;
    private double feelsLike;
    private String condition;
}
