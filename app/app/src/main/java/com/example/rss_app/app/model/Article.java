package com.example.rss_app.app.model;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    public String title, description, date, link;
    public Bitmap image;

    public Article(String title, String description, String date, Bitmap image, String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", link='" + link + '\'' +
                ", image=" + image +
                '}';
    }

    public Article(Parcel source) {
        title = source.readString();
        description = source.readString();
        date = source.readString();
        link = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(link);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>(){
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
