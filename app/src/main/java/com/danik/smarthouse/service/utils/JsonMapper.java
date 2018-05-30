package com.danik.smarthouse.service.utils;

import android.util.Log;

import com.danik.smarthouse.service.exceptions.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JsonMapper {

    public static <T> T parseJSONFromLink(String link, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        HttpClient httpClient = new HttpClient();

        String jsonString = null;
        try {
            jsonString = httpClient.execute(link).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            Log.e("tag", mapper.readValue(jsonString, clazz).toString());
            return clazz.cast(mapper.readValue(jsonString, clazz));
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new JsonParseException("can't parse " + clazz.getName());
        }
    }

    public static <T> T parseJSON(String json, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Log.e("tag", mapper.readValue(json, clazz).toString());
            return clazz.cast(mapper.readValue(json, clazz));
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new JsonParseException("can't parse " + clazz.getName());
        }
    }

    public static <T> List parseJSONToList(String json, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Log.e("tag", mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz)).toString());
            return List.class.cast(mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz)));
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new JsonParseException("can't parse " + clazz.getName());
        }
    }
}
