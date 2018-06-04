package com.danik.smarthouse.service;

import com.danik.smarthouse.model.UserData;

import java.util.List;

public interface UserDataService {

    UserData saveForClimateConfig(Long deviceId, Long climateConfigId);

    UserData saveForLightConfig(Long deviceId, Long lightConfigId);

    UserData update(UserData userData);

    UserData findOne(Long id);

    List<UserData> findAll();

    Boolean delete(Long id);

    List<UserData> findAllByUserId(Long userId);

    List<UserData> findAllByDeviceId(Long deviceId);
}
