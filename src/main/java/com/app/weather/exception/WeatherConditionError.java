package com.app.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class WeatherConditionError {

    private String errorMessage;
    private String details;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WeatherConditionError other = (WeatherConditionError) obj;
        return Objects.equals(errorMessage, other.errorMessage) && Objects.equals(details, other.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, details);
    }
}

