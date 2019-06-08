package com.example.rss_app.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rss_app.R;
import com.example.rss_app.app.adapter.NewsAdapter;
import com.example.rss_app.app.model.Article;
import com.example.rss_app.app.service.ApiClient;
import com.example.rss_app.app.service.IService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IService iService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ArrayList<Article> news = getIntent().getExtras().getParcelableArrayList("news");

        Article[] news_list = new Article[news.size()];
        for (int i = 0; i< news.size(); i++) {
            news_list[i] = news.get(i);
        }

        Button fav = findViewById(R.id.fav);
        if (getIntent().getStringExtra("fav").equals("true")) {
            fav.setVisibility(View.VISIBLE);
        } else {
            fav.setVisibility(View.INVISIBLE);
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);

                Retrofit apiClient = ApiClient.getInstance();
                iService = apiClient.create(IService.class);

                compositeDisposable.add(iService.favourites(getIntent().getStringExtra("email")).subscribeOn(
                        Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws JSONException {

                                ArrayList<String> news = new ArrayList<>();
                                ArrayList<String> titles = new ArrayList<>();
                                ArrayList<String> desc = new ArrayList<>();
                                ArrayList<String> dates = new ArrayList<>();

                                ArrayList<Article> news_adapter_list = new ArrayList<>();

                                try {

                                    JSONObject response = new JSONObject(s);
                                    JSONArray st = response.getJSONArray("news");

                                    for(int i=0;i<st.length();i++) {
                                        news.add(st.getString(i));
                                    }

                                    for(int j=0; j<news.size(); j++) {
                                        JSONObject article = new JSONObject(news.get(j));

                                        String title = article.getString("title");
                                        String description = article.getString("description");
                                        String date = article.getString("date");

                                        titles.add(title);
                                        desc.add(description);
                                        dates.add(date);

                                        news_adapter_list.add(
                                                new Article(titles.get(j), desc.get(j), dates.get(j), "", "")
                                        );
                                    }

                                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                    intent.putParcelableArrayListExtra("news", news_adapter_list);
                                    intent.putExtra("fav", "false");

                                    startActivity(intent);

                                }catch(Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.gallery);

        NewsAdapter adapter = new NewsAdapter(news_list, getIntent().getStringExtra("email"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }
}
