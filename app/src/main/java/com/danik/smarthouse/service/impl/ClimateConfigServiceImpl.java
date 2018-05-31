package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.ClimateConfig;
import com.danik.smarthouse.service.ClimateConfigService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.UserDetails;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClimateConfigServiceImpl implements ClimateConfigService {

    private static final String SERVER_URL = "http://192.168.1.232:9090/climate-config";
//    private static final String SERVER_URL = "http://192.168.1.7:9090/climate-config";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public ClimateConfig save(Double temperature, Time startTime, Time endTime, Boolean active) {
        String response = "";
        uri = "/save";
        method = "POST";
        body = new HashMap<>();
        body.put("temperature", temperature.toString());
        body.put("startTime", startTime.toString());
        body.put("endTime", endTime.toString());
        body.put("active", active.toString());
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, ClimateConfig.class);
    }

    @Override
    public ClimateConfig update(Long id,Double temperature, Time startTime, Time endTime, Boolean active) {
        String response = "";
        uri = "/update";
        method = "POST";
        body = new HashMap<>();
        body.put("id", id.toString());
        body.put("temperature", temperature.toString());
        body.put("startTime", startTime.toString());
        body.put("endTime", endTime.toString());
        body.put("active", active.toString());
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, ClimateConfig.class);
    }

    @Override
    public ClimateConfig findOne(Long id) {
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
        return JsonMapper.parseJSON(response, ClimateConfig.class);
    }

    @Override
    public List<ClimateConfig> findAll() {
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
        return JsonMapper.parseJSONToList(response, ClimateConfig.class);
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
}
