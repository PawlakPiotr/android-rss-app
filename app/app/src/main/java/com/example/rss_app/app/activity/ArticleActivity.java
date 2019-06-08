package com.example.rss_app.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rss_app.R;
import com.example.rss_app.app.adapter.ImageAsyncLoad;
import com.example.rss_app.app.service.ApiClient;
import com.example.rss_app.app.service.IService;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ArticleActivity extends AppCompatActivity {

    TextView title, content;
    ImageView img;
    Button article_link, fav;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IService iService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        img = findViewById(R.id.img);
        article_link =findViewById(R.id.see_article);
        fav = findViewById(R.id.fav);

        title.setText(getIntent().getStringExtra("title") != null ? getIntent().getStringExtra("title") : "");
        content.setText(getIntent().getStringExtra("desc") != null ? getIntent().getStringExtra("desc") : "");


        @SuppressLint("StaticFieldLeak") ImageAsyncLoad obj = new ImageAsyncLoad(
                getIntent().getStringExtra("image") != null ? getIntent().getStringExtra("image") : ""
        ){

            @Override
            protected void onPostExecute(Bitmap bmp) {
                super.onPostExecute(bmp);
                img.setImageBitmap(bmp);
            }
        };

        obj.execute();

        Retrofit apiClient = ApiClient.getInstance();
        iService = apiClient.create(IService.class);

        article_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ARTICLE_URL = getIntent().getStringExtra("link");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ARTICLE_URL));
                startActivity(browserIntent);
            }
        });


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compositeDisposable.add(iService.addToFavourites(getIntent().getStringExtra("email"), title.getText().toString(),
                                                                    content.getText().toString(), getIntent().getStringExtra("date")).subscribeOn(
                        Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws JSONException {
                                Toast.makeText(ArticleActivity.this, "Article added to favourites", Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });
    }
}
