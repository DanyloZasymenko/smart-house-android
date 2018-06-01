package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.UserData;
import com.danik.smarthouse.service.UserDataService;

import java.util.List;

public class UserDataServiceImpl implements UserDataService {

    //    private static final String SERVER_URL = "http://192.168.1.232:9090/user-data";
//    private static final String SERVER_URL = "http://192.168.1.7:9090/user-data";
    private static final String SERVER_URL = "http://mplus.hopto.org:9090/user-data";

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
