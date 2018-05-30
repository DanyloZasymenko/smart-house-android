package com.danik.smarthouse.service.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpClient extends AsyncTask<String, String, String> {

    private String uri;
    private String method;
    private Map<String, String> body;
    private Map<String, String> headers;

    public HttpClient() {

    }

    public HttpClient(String uri, String method, Map<String, String> body, Map<String, String> headers) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.headers = headers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;
        HttpURLConnection connection = null;

        try {
            final StringBuffer bodyUrl = new StringBuffer("?");
            body.entrySet().stream().forEach(stringStringEntry -> {
                bodyUrl.append(Uri.encode(stringStringEntry.getKey()));
                bodyUrl.append("=");
                bodyUrl.append(Uri.encode(stringStringEntry.getValue()));
                bodyUrl.append("&");
            });

            URL url = new URL(uri + bodyUrl.toString());
            connection = (HttpURLConnection) url.openConnection();
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                Log.i("headers", "key : " + stringStringEntry.getKey() + " value : " + stringStringEntry.getValue());
                connection.setRequestProperty(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
            connection.setRequestMethod(method);
            connection.connect();

            InputStream stream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
