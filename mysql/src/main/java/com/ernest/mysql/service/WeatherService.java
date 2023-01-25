package com.ernest.mysql.service;

import com.ernest.mysql.dto.WeatherRegRequestDto;
import com.ernest.mysql.model.Weather;
import com.ernest.mysql.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    WeatherRepository weatherRepository;


    public void runWeathercheck(WeatherRegRequestDto weatherRegRequestDto){
        KieSession ks = kieContainer.newKieSession();
        ks.insert(weatherRegRequestDto);
            ks.fireAllRules();
            ks.dispose();
    }

    //save weatherinfo to database
    public void saveWeatherInfo(WeatherRegRequestDto weatherRegRequestDto){
                    Weather weather = Weather.builder()
                    .cityName(weatherRegRequestDto.getCityName())
                    .temperature(weatherRegRequestDto.getTemperature())
                    .percentHumidity(weatherRegRequestDto.getPercentHumidity())
                    .windDirection(weatherRegRequestDto.getWindDirection())
                    .windVelocity(weatherRegRequestDto.getWindVelocity())
                    .barometer(weatherRegRequestDto.getBarometer())
                    .forecast(weatherRegRequestDto.getForecast())
                            .alert(weatherRegRequestDto.getAlertType())
                    .build();
            weatherRepository.save(weather);
    }

    //save alert to admin
    public void sendAlert(WeatherRegRequestDto weatherRegRequestDto){
        log.info("sending  {} alert....", weatherRegRequestDto.getAlertType() );
        //todo send alert to admin e.g sending mail
    }
}
