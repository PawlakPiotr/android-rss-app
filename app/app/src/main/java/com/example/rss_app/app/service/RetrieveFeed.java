package com.example.rss_app.app.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.rss_app.app.model.Article;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RetrieveFeed extends AsyncTask {

    URL url;
    ArrayList<String> headlines = new ArrayList();
    ArrayList<String> desc = new ArrayList();
    ArrayList<String> links = new ArrayList();
    ArrayList<String> dates = new ArrayList();
    ArrayList<String> images = new ArrayList();

    ArrayList<Article> news = new ArrayList<>();
    Context context;

    public RetrieveFeed(Context context) {
        this.context = context;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        Service service = new Service();

        try {
            url = new URL(service.getRssFeedUrl());

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(getInputStream(url), "UTF_8");
            boolean insideItem = false;

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            headlines.add(xpp.nextText());
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            links.add(xpp.nextText());
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem) {
                            desc.add(xpp.nextText());

//                            String url = xpp.nextText().substring(19, xpp.nextText().indexOf("quality=80")).replaceAll("amp;", "");
                        //    images.add(url);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem)
                            dates.add(xpp.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }

                eventType = xpp.next();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return headlines;
    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<String> heads() {
        return headlines;
    }

    public ArrayList<String> links() {
        return links;
    }

    public ArrayList<String> desc() {
        return desc;
    }

    public ArrayList<String> date() {
        return dates;
    }

    public ArrayList<String> images() { return images; }

    public ArrayList<Article> getNews() {

        for (int i=0; i< headlines.size(); i++) {
            news.add(
                    new Article(headlines.get(i), desc.get(i), dates.get(i), null, links.get(i))
            );
        }

        return news;
    }
}