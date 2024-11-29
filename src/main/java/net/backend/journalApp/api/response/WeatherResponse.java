package net.backend.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * A response model class representing the structure of the weather data retrieved from an API.
 * This class utilizes nested classes and annotations to map JSON data to Java objects.
 */
@Getter
@Setter
public class WeatherResponse {

    /**
     * Represents the current weather details.
     * This field will hold the nested `Current` object with detailed weather information.
     */
    private Current current;

    /**
     * A nested class representing the detailed current weather information.
     * This includes temperature, weather descriptions, and the "feels like" temperature.
     */
    @Getter
    @Setter
    public class Current {

        /**
         * The current temperature at the location.
         */
        private int temperature;

        /**
         * A list of descriptive weather conditions (e.g., "Sunny", "Cloudy").
         * The property is mapped from the JSON key `weather_descriptions` using the `@JsonProperty` annotation.
         */
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        /**
         * The "feels like" temperature, which accounts for factors like wind chill and humidity.
         */
        private int feelslike;
    }
}
