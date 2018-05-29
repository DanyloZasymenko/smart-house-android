package com.danik.smarthouse.model;

import java.sql.Time;

public class LightConfig {

    private Long id;
    private Time startTime;
    private Time endTime;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public LightConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public Time getStartTime() {
        return startTime;
    }

    public LightConfig setStartTime(Time startTime) {
        this.startTime = startTime;
        return this;
    }

    public Time getEndTime() {
        return endTime;
    }

    public LightConfig setEndTime(Time endTime) {
        this.endTime = endTime;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public LightConfig setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return "LightConfig{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", active=" + active +
                '}';
    }
}
