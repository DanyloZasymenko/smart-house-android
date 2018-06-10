package com.danik.smarthouse.service.utils;

import com.danik.smarthouse.model.User;

public class UserDetails {

    public static Boolean temperatureCelsius = true;

    public static String accessToken = null;

    public static User user = null;

    public static Boolean logout() {
        try {
            accessToken = null;
            user = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
