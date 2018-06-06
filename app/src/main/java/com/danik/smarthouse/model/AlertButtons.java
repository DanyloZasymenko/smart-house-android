package com.danik.smarthouse.model;

public class AlertButtons {

    private Boolean fire = false;
    private Boolean police = false;

    private static AlertButtons instance = new AlertButtons();

    public static AlertButtons getInstance() {
        return instance;
    }

    private AlertButtons() {
    }

    public AlertButtons setValues(AlertButtons alertButtons){
        this.fire = alertButtons.getFire();
        this.police = alertButtons.getPolice();
        return this;
    }

    public Boolean getFire() {
        return fire;
    }

    public AlertButtons setFire(Boolean fire) {
        this.fire = fire;
        return this;
    }

    public Boolean getPolice() {
        return police;
    }

    public AlertButtons setPolice(Boolean police) {
        this.police = police;
        return this;
    }

    public static void setInstance(AlertButtons instance) {
        AlertButtons.instance = instance;
    }

    @Override
    public String toString() {
        return "AlertButtons{" +
                "fire=" + fire +
                ", police=" + police +
                '}';
    }
}
