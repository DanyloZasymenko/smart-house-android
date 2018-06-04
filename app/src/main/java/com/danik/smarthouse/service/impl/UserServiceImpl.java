package com.danik.smarthouse.service.impl;

import android.util.Log;

import com.danik.smarthouse.model.User;
import com.danik.smarthouse.service.UserService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.Url;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserServiceImpl implements UserService {

    private final String SERVER_URL = Url.url + "/user";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public User getUser() {
        String response = "";
        uri = "/";
        method = "GET";
        body = new HashMap<>();
        headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        Log.i("access_token", UserDetails.accessToken.toString());
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
            Log.i("response", response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            return JsonMapper.parseJSON(response, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User save(String name, String middleName, String lastName, String email, String password) {
        String response = "";
        uri = "/save";
        method = "POST";
        body = new HashMap<>();
        body.put("name", name);
        body.put("middleName", middleName);
        body.put("lastName", lastName);
        body.put("email", email);
        body.put("password", password);
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return JsonMapper.parseJSON(response, User.class);
    }

    @Override
    public User update(Long id, String name, String middleName, String lastName, String email, String password, Float temperature) {
        String response = "";
        uri = "/update";
        method = "POST";
        body = new HashMap<>();
        body.put("id", id.toString());
        body.put("name", name);
        body.put("middleName", middleName);
        body.put("lastName", lastName);
        body.put("email", email);
        body.put("password", password);
        body.put("temperature", temperature.toString());
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return JsonMapper.parseJSON(response, User.class);
    }

    @Override
    public User findOne(Long id) {
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
        return JsonMapper.parseJSON(response, User.class);
    }

    @Override
    public List<User> findAll() {
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
        return JsonMapper.parseJSONToList(response, User.class);
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
    public User findByEmail(String email) {
        String response = "";
        uri = "/find-by-email/" + email;
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
        return JsonMapper.parseJSON(response, User.class);
    }

    @Override
    public List<User> findAllByHouseId(Long houseId) {
        String response = "";
        uri = "/find-all-by-house-id/" + houseId;
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
        return JsonMapper.parseJSONToList(response, User.class);
    }
}
