package com.example.rbt_praksa_android.model;

public class Temperature {
    private float temperature;
    private long timestamp;

    public Temperature(float temperature, long timestamp) {
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
