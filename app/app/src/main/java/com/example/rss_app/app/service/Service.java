package com.example.rss_app.app.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Service {

    private static final String PORT = "3000";
    private static final String[] IP_ADDRESS = { "192.168.0.45", "10.0.2.2"};
    private static final String[] PROTOCOL = {"http", "https"};

    private static final String API_BASE_URL = PROTOCOL[0] + "://" + IP_ADDRESS[1] + ":" + PORT;

    private static final String RSS_FEED_URL = "https://www.tvn24.pl/najnowsze.xml";

    public void get(String API_ENDPOINT) throws IOException {
        URL url = new URL(API_BASE_URL + API_ENDPOINT);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();
        if (statusCode ==  200) {
            InputStream it = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            StringBuilder dta = new StringBuilder();
            String chunks ;
            while((chunks = buff.readLine()) != null)
            {
                dta.append(chunks);
            }
            System.out.println("####### => " + dta.toString());
        }
    }

    public String getRssFeedUrl() {
        return RSS_FEED_URL;
    }
}
