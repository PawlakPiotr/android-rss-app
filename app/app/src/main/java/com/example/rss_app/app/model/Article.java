package com.example.rss_app.app.model;

import android.graphics.Bitmap;
import android.media.Image;

public class Article {

    public String title, description, date;
    public Bitmap image;

    public Article(String title, String description, String date, Bitmap image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }
}
