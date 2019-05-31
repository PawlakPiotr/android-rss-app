package com.example.rss_app.app.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rss_app.R;
import com.example.rss_app.app.adapter.NewsAdapter;
import com.example.rss_app.app.model.Article;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        Article[] items = {
                new Article("Jakis tam artykul 1", "as jas oidjais doiasjd aoisj doia sdioasj diaois d", "27.11.2019",  null),
                new Article("Jakis tam artykul 2", "oidsf8213 asidja ois9832wq89ezsxcdj asi daos jda", "02.08.2019",  null),
                new Article("Jakis tam artykul 3", "as jas oidjais doiasjd aoisj doia sdioasj diaois d", "20.12.2019",  null),
                new Article("Jakis tam artykul 4", "oidsf8213 asidja ois9832wq89ezsxcdj asi daos jda", "12.02.2019",  null),
        };


        RecyclerView recyclerView = findViewById(R.id.gallery);

        NewsAdapter adapter = new NewsAdapter(items);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }
}
