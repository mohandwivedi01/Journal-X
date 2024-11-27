package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

//@Component
@Service
public class WeatherServices {
    @Autowired
    private UserServices userServices;

    /*
    @Value("${weather.api.key}")
    private static String APIKEY ;

    spring creates a bean of WeatherServices currently it has single instance but some have
    multiple instances so Spring does not touch any static variable because static variable
    related to class, all the instance share the static member so spring does not affect any
    static variable
    that s why we make 'APIKEY' as non st atic
     */
    @Value("${weather.api.key}")
    private String APIKEY;

//    private static final String API = "http://api.weatherstack.com/current?access_key=<API_KEY>&&query=<CITY>";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        String apiRequest = appCache.APP_CACHE.get("weather_api").replace("<CITY>", city).replace("<API_KEY>", APIKEY);
        /*
        RestTemplet is a class in Spring which process the http request and provide us responses

        apiRequest            ------>   hit this api
        HttpMethod.GET        ------>   using Get method
        null                  ------>   Header is null
        WeatherResponse.class ------>   deserialize the response(the process of converting JSON
                                        response into corresponding Java object is deserialization ) */
        ResponseEntity<WeatherResponse> apiResponse = restTemplate.exchange(apiRequest, HttpMethod.GET, null, WeatherResponse.class);

        return apiResponse.getBody();

    }

}
