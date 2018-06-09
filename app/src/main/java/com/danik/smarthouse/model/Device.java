package com.danik.smarthouse.model;

import com.danik.smarthouse.model.enums.DeviceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Device {

    private Long id;
    private String name;
    private Integer pin;
    private DeviceType deviceType;
    private Boolean active;
    @JsonIgnore
    private House house;

    public Long getId() {
        return id;
    }

    public Device setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Device setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPin() {
        return pin;
    }

    public Device setPin(Integer pin) {
        this.pin = pin;
        return this;
    }

    @JsonIgnore
    public House getHouse() {
        return house;
    }

    @JsonIgnore
    public Device setHouse(House house) {
        this.house = house;
        return this;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Device setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public Device setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    //    @Override
//    public String toString() {
//        return "Device{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", pin=" + pin +
//                ", deviceType=" + deviceType +
//                ", active=" + active +
//                '}';
//    }
}
