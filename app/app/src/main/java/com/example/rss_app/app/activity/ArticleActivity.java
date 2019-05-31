package com.example.rss_app.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rss_app.R;

public class ArticleActivity extends AppCompatActivity {

    TextView title, content;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        img = findViewById(R.id.img);

        title.setText(getIntent().getStringExtra("title") != null ? getIntent().getStringExtra("title") : "");
        content.setText(getIntent().getStringExtra("desc") != null ? getIntent().getStringExtra("desc") : "");

        img.setImageBitmap(null); // TODO set article image
    }
}
