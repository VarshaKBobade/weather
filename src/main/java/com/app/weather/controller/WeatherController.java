package com.app.weather.controller;

import com.app.weather.exception.IPGeolocationException;
import com.app.weather.exception.WeatherConditionError;
import com.app.weather.exception.WeatherServiceException;
import com.app.weather.model.GeoLocation;
import com.app.weather.model.WeatherCondition;
import com.app.weather.service.IPLocatorService;
import com.app.weather.service.WeatherService;
import com.app.weather.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private IPLocatorService ipLocatorService;
    @Autowired
    private WeatherService weatherService;


    @GetMapping
    public ResponseEntity<?> getWeatherByIP(HttpServletRequest request) {
        String ipAddress = HttpUtils.getRequestIP(request); //"144.36.114.157";
        if(ipAddress.contains("0:0:0:0:0:0:0")){
            return ResponseEntity.ok("calling from localhost. invalid IP");
        }
        try {
            // Perform reverse geolocation search using IP address
            GeoLocation location = ipLocatorService.geolocationSearch(ipAddress);

            // Retrieve weather data using the obtained coordinates
            WeatherCondition weatherCondition = weatherService.getWeatherData(location.getLatitude(), location.getLongitude());
            return ResponseEntity.ok(weatherCondition);

        }  catch (WeatherServiceException ex) {
            WeatherConditionError errorResponse = new WeatherConditionError("Failed to fetch weather data.", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (IPGeolocationException ex) {
            WeatherConditionError errorResponse = new WeatherConditionError("Failed to fetch location details.", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception ex) {
            WeatherConditionError errorResponse = new WeatherConditionError("An error occurred while fetching weather data.", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}

