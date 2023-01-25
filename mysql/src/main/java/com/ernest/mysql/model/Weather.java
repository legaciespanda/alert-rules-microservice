package com.ernest.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Weather Model
 * @author Ernest Obot
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city_name")
    private String cityName;
    @Column(name = "temperature")
    private Integer temperature;
    @Column(name = "percentage_humidity")
    private Integer percentHumidity;
    @Column(name = "wind_direction")
    private String windDirection;
    @Column(name = "wind_velocity")
    private Integer windVelocity;
    @Column(name = "barometer")
    private Float barometer;
    @Column(name = "forecast")
    private String forecast;

    @Column(name = "alert")
    private String alert;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
