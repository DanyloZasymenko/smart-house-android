package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;
import com.danik.smarthouse.service.AndroidService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.Url;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AndroidServiceImpl implements AndroidService {

    private final String SERVER_URL = Url.url + "/android";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public Device changeActive(Long deviceId, Boolean active) {
        String response = "";
        uri = "/change-active/" + deviceId + "/" + active;
        method = "POST";
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
        try {
            return JsonMapper.parseJSON(response, Device.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Temperature getTemperature() {
        String response = "";
        uri = "/get-temperature";
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
        return JsonMapper.parseJSON(response, Temperature.class);
    }

    private String get(String uri, Map<String, String> args, Map<String, String> headers) {
        String response = "";
        uri = uri;
        method = "POST";
        httpClient = new HttpClient(SERVER_URL + uri, method, args, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }
}
