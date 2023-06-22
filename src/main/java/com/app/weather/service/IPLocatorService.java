package com.app.weather.service;

import com.app.weather.exception.IPGeolocationException;
import com.app.weather.model.GeoLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IPLocatorService {

    private static final String API_KEY = "cb6a67d9efec48e8b64638c0d8642412";
    private static final String API_BASE_URL = "https://api.ipgeolocation.io/ipgeo";

    public GeoLocation geolocationSearch(String ipAddress)  {
        ObjectMapper objectMapper=new ObjectMapper();
        RestTemplate restTemplate=new RestTemplate();
        try{
            String apiUrl = API_BASE_URL + "?apiKey=" + API_KEY + "&ip=" + ipAddress;
            String location = restTemplate.getForObject(apiUrl, String.class);
            GeoLocation loc=objectMapper.readValue(location,GeoLocation.class);
            return loc;
        }
        catch (JsonProcessingException e){
            throw new IPGeolocationException(e.getMessage());
        }
        catch (Exception e){
            throw new IPGeolocationException(e.getMessage());
        }

    }
}

