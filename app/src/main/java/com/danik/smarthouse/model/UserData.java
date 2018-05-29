package com.danik.smarthouse.model;

public class UserData {

    private Long id;
    private User user;
    private Device device;
    private ClimateConfig climateConfig;
    private LightConfig lightConfig;

    public Long getId() {
        return id;
    }

    public UserData setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserData setUser(User user) {
        this.user = user;
        return this;
    }

    public Device getDevice() {
        return device;
    }

    public UserData setDevice(Device device) {
        this.device = device;
        return this;
    }

    public ClimateConfig getClimateConfig() {
        return climateConfig;
    }

    public UserData setClimateConfig(ClimateConfig climateConfig) {
        this.climateConfig = climateConfig;
        return this;
    }

    public LightConfig getLightConfig() {
        return lightConfig;
    }

    public UserData setLightConfig(LightConfig lightConfig) {
        this.lightConfig = lightConfig;
        return this;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", device=" + device.getId() +
                ", climateConfig=" + climateConfig.getId() +
                ", lightConfig=" + lightConfig.getId() +
                '}';
    }
}
