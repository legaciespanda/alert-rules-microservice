package com.ernest.influx.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Weather data transfer object
 * @author Ernest Obot
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRegRequestDto {

    @NotNull
    private String cityName;
    @NotNull
    private Integer temperature;
    @NotNull
    private Integer percentHumidity;
    @NotNull
    private String windDirection;
    @NotNull
    private Integer windVelocity;
    @NotNull
    private Float barometer;
    @NotNull
    private String forecast;

    private String alertType;
}
