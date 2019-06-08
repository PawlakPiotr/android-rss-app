package com.example.rss_app.app.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAsyncLoad extends AsyncTask<Void, Void, Bitmap> {

    String url_address;

    public ImageAsyncLoad(String url) {
        this.url_address = url;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        try {
            URL url = new URL(url_address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}