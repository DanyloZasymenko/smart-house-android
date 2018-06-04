package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.UserData;
import com.danik.smarthouse.service.UserDataService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.Url;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserDataServiceImpl implements UserDataService {

    private final String SERVER_URL = Url.url + "/user-data";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public UserData saveForClimateConfig(Long deviceId, Long climateConfigId) {
        String response = "";
        uri = "/save-for-climate-config";
        method = "POST";
        body = new HashMap<>();
        body.put("deviceId", deviceId.toString());
        body.put("climateConfigId", climateConfigId.toString());
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return JsonMapper.parseJSON(response, UserData.class);
    }

    @Override
    public UserData saveForLightConfig(Long deviceId, Long lightConfigId) {
        String response = "";
        uri = "/save-for-light-config";
        method = "POST";
        body = new HashMap<>();
        body.put("deviceId", deviceId.toString());
        body.put("lightConfigId", lightConfigId.toString());
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return JsonMapper.parseJSON(response, UserData.class);
    }

    @Override
    public UserData update(UserData userData) {
        return null;
    }

    @Override
    public UserData findOne(Long id) {
        String response = "";
        uri = "/find-one/" + id;
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, UserData.class);

    }

    @Override
    public List<UserData> findAll() {
        String response = "";
        uri = "/find-all";
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSONToList(response, UserData.class);
    }

    @Override
    public Boolean delete(Long id) {
        String response = "";
        uri = "/delete/" + id;
        method = "DELETE";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, Boolean.class);
    }


    @Override
    public List<UserData> findAllByUserId(Long userId) {
        String response = "";
        uri = "/find-all-by-user-id/" + userId;
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSONToList(response, UserData.class);
    }

    @Override
    public List<UserData> findAllByDeviceId(Long deviceId) {
        String response = "";
        uri = "/find-all-by-device-id/" + deviceId;
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSONToList(response, UserData.class);

    }
}
