package com.ernest.influx.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
}
