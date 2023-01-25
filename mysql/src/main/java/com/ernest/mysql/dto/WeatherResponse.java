package com.ernest.mysql.dto;


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
