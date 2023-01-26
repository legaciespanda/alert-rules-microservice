package com.ernest.influx.controller;


import com.ernest.influx.dto.WeatherRegRequestDto;
import com.ernest.influx.dto.WeatherResponse;
import com.ernest.influx.service.WeatherService;
import com.ernest.influx.dto.WeatherRegRequestDto;
import com.ernest.influx.dto.WeatherResponse;
import com.ernest.influx.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/weather")

public class WeatherController {
    private static final String TAG = "WeatherController";

    @Autowired
    WeatherService weatherService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> createWeather(@RequestBody WeatherRegRequestDto weatherRegRequestDto){

        weatherService.runWeathercheck(weatherRegRequestDto);

        //check for alert type
        if(weatherRegRequestDto.getAlertType() == "critical"){

            //save weather data
            log.info("{} Alert " , weatherRegRequestDto.getAlertType());
            //send critical alert
            weatherService.sendAlert(weatherRegRequestDto);
        }

        if(weatherRegRequestDto.getAlertType() == "minor"){
            //send minor alert
            log.info("{} Alert " , weatherRegRequestDto.getAlertType());
            weatherService.sendAlert(weatherRegRequestDto);

        }
        weatherService.saveWeatherInfo(weatherRegRequestDto);

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setRequestSuccessful(true);
        weatherResponse.setResponseMessage("Weather data has been created successfully");
        weatherResponse.setResponseCode("200");

            return ResponseEntity.status(HttpStatus.OK)
                .body(weatherResponse);
    }

}
