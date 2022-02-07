package com.example.demo.iot;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class TemperatureEvent {
    /**
     * ID of the temperature sensor
     */
    private String sensorId;

    /**
     * Measured temperature on the Celsius scale.
     */
    private Float tempC;

    /**
     * Moment of time when temperature was measured.
     * Formatted in RFC3339.
     */
    private String created;

    private TemperatureEvent() {

    }

    public static TemperatureEvent collect(TemperatureSensor sensor)
    {
        var event = new TemperatureEvent();
        event.sensorId = sensor.getId();
        event.tempC = sensor.measure();
        event.created = DateTimeFormatter.ISO_INSTANT.format( Instant.now() );
        return event;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getSensorId() {
        return sensorId;
    }

    public Float getTempC() {
        return tempC;
    }

    public String getCreated() {
        return created;
    }
}
