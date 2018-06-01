package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.utils.HttpClient;
import com.danik.smarthouse.service.utils.JsonMapper;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DeviceServiceImpl implements DeviceService {

    //        private static final String SERVER_URL = "http://192.168.1.232:9090/device";
//    private static final String SERVER_URL = "http://192.168.1.7:9090/device";
    private static final String SERVER_URL = "http://mplus.hopto.org:9090/device";
    private String uri = null;
    private String method = null;
    private Map<String, String> body;
    private Map<String, String> headers;
    private HttpClient httpClient;

    @Override
    public Device save(String name, Integer pin, String deviceType, Long houseId) {
        String response = "";
        uri = "/save";
        method = "POST";
        body = new HashMap<>();
        body.put("name", name);
        body.put("pin", pin.toString());
        body.put("deviceType", deviceType);
        body.put("houseId", houseId.toString());
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, Device.class);
    }

    @Override
    public Device update(Long id, String name, Integer pin, String deviceType, Long houseId) {
        String response = "";
        uri = "/update";
        method = "POST";
        body = new HashMap<>();
        body.put("id", id.toString());
        body.put("name", name);
        body.put("pin", pin.toString());
        body.put("deviceType", deviceType);
        body.put("houseId", houseId.toString());
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserDetails.accessToken);
        headers.put("Content-Type", "application/json; charset=UTF-8");
        httpClient = new HttpClient(SERVER_URL + uri, method, body, headers);
        try {
            response = httpClient.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JsonMapper.parseJSON(response, Device.class);
    }

    @Override
    public Device findOne(Long id) {
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
        return JsonMapper.parseJSON(response, Device.class);
    }

    @Override
    public List<Device> findAll() {
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
        return JsonMapper.parseJSONToList(response, Device.class);
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
    public Device findByNameAndHouseId(String name, Long houseId) {
        String response = "";
        uri = "/find-by-name-and-house-id/" + name + "/" + houseId;
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
        return JsonMapper.parseJSON(response, Device.class);
    }

    @Override
    public List<Device> findAllByHouseId(Long houseId) {
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
        return JsonMapper.parseJSONToList(response, Device.class);
    }

    @Override
    public List<Device> findAllByActiveAndHouseId(Boolean active, Long houseId) {
        String response = "";
        uri = "/find-all-by-active-and-house-id/" + active + "/" + houseId;
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
        return JsonMapper.parseJSONToList(response, Device.class);
    }
}
