package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.UserData;
import com.danik.smarthouse.service.UserDataService;
import com.danik.smarthouse.service.utils.Url;

import java.util.List;

public class UserDataServiceImpl implements UserDataService {

    private final String SERVER_URL = Url.url + "/user-data";

    @Override
    public UserData save(UserData userData) {
        return null;
    }

    @Override
    public UserData update(UserData userData) {
        return null;
    }

    @Override
    public UserData findOne(Long id) {
        return null;
    }

    @Override
    public List<UserData> findAll() {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<UserData> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public List<UserData> findAllByDeviceId(Long deviceId) {
        return null;
    }
}
