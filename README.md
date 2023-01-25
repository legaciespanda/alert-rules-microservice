## Weather Microservice

### Introduction

A Weather microservice which applies rules on different entities parameter located in MySQL and influxDB database


#### - Technology Stack
- Spring Boot
- Spring Data JPA
- Hibernate
- InfluxDb & MySQL
- Drools
- Lombok
- Validation

#### - Features
 - If the temperature is above 40 degrees, it will make/trigger an instant `critical` alert
   - If the temperature is above 30 degree and percent humidity is above 10, it will trigger an instant `minor alert to the admin
 - All triggered alerts are saved to the database
 - uses drools rule to trigger respective alerts


#### - API REQUEST CODE SNIPPET
```
curl --location --request POST 'http://localhost:2023/api/v1/weather' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cityName" : "Lagos",
    "temperature": 43,
    "percentHumidity": 9,
    "windDirection" : "West",
    "windVelocity" : 12,
    "barometer": 4.5,
    "forecast": "ernest"
}'
```

#### - API RESPONSE CODE SNIPPET
```
{
    "requestSuccessful": true,
    "responseMessage": "Weather data has been created successfully",
    "responseCode": "200"
}
```


