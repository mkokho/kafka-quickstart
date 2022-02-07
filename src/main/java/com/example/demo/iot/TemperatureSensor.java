package com.example.demo.iot;

public class TemperatureSensor {
    /**
     * ID of a sensor.
     */
    private final String id;

    public TemperatureSensor(String id) {
        this.id = id;
    }

    public Float measure()
    {
        return org.apache.commons.lang3.RandomUtils.nextFloat(0, 200) - 80;
    }

    public String getId() {
        return id;
    }
}
