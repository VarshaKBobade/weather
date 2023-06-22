package com.app.weather.controller;

import com.app.weather.exception.IPGeolocationException;
import com.app.weather.exception.WeatherConditionError;
import com.app.weather.exception.WeatherServiceException;
import com.app.weather.model.GeoLocation;
import com.app.weather.model.WeatherCondition;
import com.app.weather.service.IPLocatorService;
import com.app.weather.service.WeatherService;
import com.app.weather.util.HttpUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherControllerTest {

    @Mock
    private IPLocatorService ipLocatorService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    public void testGetWeatherByIP_ValidIP() throws IPGeolocationException, WeatherServiceException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        when(ipLocatorService.geolocationSearch(anyString())).thenReturn(new GeoLocation(32.87, -96.94));
        when(weatherService.getWeatherData(32.87, -96.94)).thenReturn(new WeatherCondition(28.3, 85, 32.4,"Overcast"));

        ResponseEntity<?> response = weatherController.getWeatherByIP(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new WeatherCondition(28.3, 85,  32.4,"Overcast"), response.getBody());
    }

    @Test
    public void testGetWeatherByIP_InvalidIP() {
        HttpServletRequest request = new MockHttpServletRequest();
        when(ipLocatorService.geolocationSearch(any())).thenThrow(new IPGeolocationException("Invalid IP address"));

        ResponseEntity<?> response = weatherController.getWeatherByIP(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new WeatherConditionError("Failed to fetch location details.", "Invalid IP address"), response.getBody());
    }

    @Test
    public void testGetWeatherByIP_WeatherServiceException() throws IPGeolocationException {
        HttpServletRequest request = new MockHttpServletRequest();
        when(ipLocatorService.geolocationSearch(anyString())).thenReturn(new GeoLocation(32.87, -96.94));
        when(weatherService.getWeatherData(32.87, -96.94)).thenThrow(new WeatherServiceException("Failed to fetch weather data."));

        ResponseEntity<?> response = weatherController.getWeatherByIP(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new WeatherConditionError("Failed to fetch weather data.", "Failed to fetch weather data."), response.getBody());
    }

    @Test
    public void testGetWeatherByIP_IPGeolocationException() throws IPGeolocationException {
        HttpServletRequest request = new MockHttpServletRequest();
        when(ipLocatorService.geolocationSearch(anyString())).thenThrow(new IPGeolocationException("Failed to fetch location details."));

        ResponseEntity<?> response = weatherController.getWeatherByIP(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new WeatherConditionError("Failed to fetch location details.", "Failed to fetch location details."), response.getBody());
    }
}
