package com.app.weather.service;

import com.app.weather.exception.WeatherServiceException;
import com.app.weather.model.WeatherApiResponse;
import com.app.weather.model.WeatherCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class WeatherService {

    private static final String API_KEY = "e5c4ad1ef7944ae58e545451232106";
    private static final String API_BASE_URL = "http://api.weatherapi.com/v1/current.json";

    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.objectMapper = new ObjectMapper();
    }

    public WeatherCondition getWeatherData(double latitude, double longitude){
        String apiUrl = API_BASE_URL + "?key=" + API_KEY + "&q=" + latitude + "," + longitude + "&aqi=no";
        try {
            URL url = new URL(apiUrl);

            // Make the API call and retrieve the response
            WeatherApiResponse apiResponse = objectMapper.readValue(url, WeatherApiResponse.class);

            // Extract the relevant weather data from the API response
            WeatherCondition weatherCondition = extractWeatherCondition(apiResponse);

            return weatherCondition;
        }catch(IOException e){
            throw new WeatherServiceException(e.getMessage());
        }catch (Exception e){
            throw new WeatherServiceException(e.getMessage());
        }
    }

    private WeatherCondition extractWeatherCondition(WeatherApiResponse apiResponse) {

        // Extract and map the relevant weather data from the API response to a WeatherCondition object
        // Customize this method based on the structure of the API response and your WeatherCondition class
        WeatherCondition weatherCondition = WeatherCondition.builder()
                .temperature(apiResponse.getCurrent().getTempC())
                .humidity(apiResponse.getCurrent().getHumidity())
                .feelsLike(apiResponse.getCurrent().getFeelsLikeC())
                .condition(apiResponse.getCurrent().getCondition().getText()).build();
        return weatherCondition;
    }
}

