package com.danik.smarthouse.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class House {

    private Long id;
    private String name;
    private String serial;
    private Boolean active;
    private Boolean online;
    private Timestamp dateOnline;
    private List<Device> devices;
    private List<User> users;

    public Long getId() {
        return id;
    }

    public House setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public House setName(String name) {
        this.name = name;
        return this;
    }

    public String getSerial() {
        return serial;
    }

    public House setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public House setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Boolean getOnline() {
        return online;
    }

    public House setOnline(Boolean online) {
        this.online = online;
        return this;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public House setDevices(List<Device> devices) {
        this.devices = devices;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public House setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public Timestamp getDateOnline() {
        return dateOnline;
    }

    public House setDateOnline(Timestamp dateOnline) {
        this.dateOnline = dateOnline;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", active=" + active +
                ", online=" + online +
                ", devices=" + devices.stream().map(Device::getId).collect(toList()) +
                ", users=" + users.stream().map(User::getId).collect(toList()) +
                '}';
    }
}
