package com.ernest.influx.service;


import com.ernest.influx.dto.WeatherRegRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Weatther Service
 * @author Ernest Obot
 */
@Service
@Slf4j
public class WeatherService {

    @Autowired
    KieContainer kieContainer;


    @Autowired
    private InfluxDBService influxDBService;


    public void runWeathercheck(WeatherRegRequestDto weatherRegRequestDto){
        KieSession ks = kieContainer.newKieSession();
        ks.insert(weatherRegRequestDto);
            ks.fireAllRules();
            ks.dispose();
    }

    //save weatherinfo to database
    public void saveWeatherInfo(WeatherRegRequestDto weatherRegRequestDto){

                    String measurement = "weather"; //database name

                    Map<String,String> tags = new HashMap<>();
                    tags.put("city_name",weatherRegRequestDto.getCityName());
                    tags.put("temperature", String.valueOf(weatherRegRequestDto.getTemperature()));
                    tags.put("percent_humidity", String.valueOf(weatherRegRequestDto.getPercentHumidity()));
                    tags.put("wind_direction", weatherRegRequestDto.getWindDirection());

                    Map<String, Object> fields = new HashMap<>();
                    fields.put("wind_velocity", weatherRegRequestDto.getWindVelocity());
                    fields.put("barometer",weatherRegRequestDto.getBarometer());
                    fields.put("forecast",weatherRegRequestDto.getForecast());
                    fields.put("alert",weatherRegRequestDto.getAlertType());
                    influxDBService.insert(measurement, tags, fields);
    }

    //save alert to admin
    public void sendAlert(WeatherRegRequestDto weatherRegRequestDto){
        log.info("sending  {} alert....", weatherRegRequestDto.getAlertType() );
        //todo send alert to admin e.g sending mail
    }
}
