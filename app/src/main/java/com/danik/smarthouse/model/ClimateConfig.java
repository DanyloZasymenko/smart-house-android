package com.danik.smarthouse.model;

import java.sql.Time;

public class ClimateConfig {

    private Long id;
    private Double temperature;
    private Time startTime;
    private Time endTime;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public ClimateConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getTemperature() {
        return temperature;
    }

    public ClimateConfig setTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public Time getStartTime() {
        return startTime;
    }

    public ClimateConfig setStartTime(Time startTime) {
        this.startTime = startTime;
        return this;
    }

    public Time getEndTime() {
        return endTime;
    }

    public ClimateConfig setEndTime(Time endTime) {
        this.endTime = endTime;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public ClimateConfig setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return "ClimateConfig{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", active=" + active +
                '}';
    }
}
