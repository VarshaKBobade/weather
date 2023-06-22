package com.app.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocation {
    @JsonProperty(value = "latitude")
    private double latitude;
    @JsonProperty(value = "longitude")
    private double longitude;
}
