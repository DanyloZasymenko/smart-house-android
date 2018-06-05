package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.House;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.Url;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HouseServiceImpl implements HouseService {

    private final String SERVER_URL = Url.url + "/house";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public House save(String name, String serial) {
        String response = "";
        uri = "/save";
        method = "POST";
        body = new HashMap<>();
        body.put("name", name);
        body.put("serial", serial);
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, House.class);
    }

    @Override
    public House update(Long id, String name, String serial) {
        String response = "";
        uri = "/update";
        method = "POST";
        body = new HashMap<>();
        body.put("id", id.toString());
        body.put("name", name);
        body.put("serial", serial);
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, House.class);
    }

    @Override
    public Boolean getStatus() {
        String response = "";
        uri = "/online";
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
            return response.equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public House findOne(Long id) {
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
        return JsonMapper.parseJSON(response, House.class);
    }

    @Override
    public List<House> findAll() {
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
        return JsonMapper.parseJSONToList(response, House.class);
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
    public House findBySerial(String serial) {
        String response = "";
        uri = "/find-by-serial/" + serial;
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
        return JsonMapper.parseJSON(response, House.class);
    }

    @Override
    public House createOrFindBySerial(String serial) {
        String response = "";
        uri = "/create-or-find-by-serial/" + serial;
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
        return JsonMapper.parseJSON(response, House.class);
    }
}
