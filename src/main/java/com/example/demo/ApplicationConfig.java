package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${application.sensors.count}")
    public int sensorCount;

    @Value("${application.sensors.measurements.topic}")
    public String sensorTopic;
}
