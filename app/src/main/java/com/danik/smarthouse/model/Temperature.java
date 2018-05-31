package com.danik.smarthouse.model;

public class Temperature {

    private static final Temperature instance = new Temperature();
    private Float humidity;
    private Float temperatureC;
    private Float temperatureF;
    private Float heatIndexC;
    private Float heatIndexF;

    private Temperature() {
    }

    public static Temperature getInstance() {
        return instance;
    }

    public Temperature setValues(Temperature temperature) {
        this.humidity = temperature.getHumidity();
        this.temperatureC = temperature.getTemperatureC();
        this.temperatureF = temperature.getTemperatureF();
        this.heatIndexC = temperature.getHeatIndexC();
        this.heatIndexF = temperature.getHeatIndexF();
        return this;
    }

    public Float getHumidity() {
        return humidity;
    }

    public Temperature setHumidity(Float humidity) {
        this.humidity = humidity;
        return this;
    }

    public Float getTemperatureC() {
        return temperatureC;
    }

    public Temperature setTemperatureC(Float temperatureC) {
        this.temperatureC = temperatureC;
        return this;
    }

    public Float getTemperatureF() {
        return temperatureF;
    }

    public Temperature setTemperatureF(Float temperatureF) {
        this.temperatureF = temperatureF;
        return this;
    }

    public Float getHeatIndexC() {
        return heatIndexC;
    }

    public Temperature setHeatIndexC(Float heatIndexC) {
        this.heatIndexC = heatIndexC;
        return this;
    }

    public Float getHeatIndexF() {
        return heatIndexF;
    }

    public Temperature setHeatIndexF(Float heatIndexF) {
        this.heatIndexF = heatIndexF;
        return this;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "humidity=" + humidity + " %" +
                ", temperatureC=" + temperatureC + " *C" +
                ", temperatureF=" + temperatureF + " *F" +
                ", heatIndexC=" + heatIndexC + " *C" +
                ", heatIndexF=" + heatIndexF + " *F" +
                '}';
    }
}
