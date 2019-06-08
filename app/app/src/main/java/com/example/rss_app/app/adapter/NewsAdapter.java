package com.example.rss_app.app.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rss_app.R;
import com.example.rss_app.app.activity.ArticleActivity;
import com.example.rss_app.app.model.Article;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Article[] itemsData;
    String user_email;

    public NewsAdapter(Article[] itemsData, String user_email) {
        this.itemsData = itemsData;
        this.user_email = user_email;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        String desc = itemsData[position].description.substring(itemsData[position].description.indexOf(">")+20).substring(0, itemsData[position].description.length() > 40 ? 40 : itemsData[position].description.length()) + "...";


        viewHolder.title.setText(itemsData[position].title);
        viewHolder.desc.setText(itemsData[position].description.contains("/>") ? desc : itemsData[position].description);
        viewHolder.date.setText(itemsData[position].date.substring(0, itemsData[position].date.lastIndexOf(":")));


        System.out.println("IMAGE TO LOAD -> " + itemsData[position].image);


        @SuppressLint("StaticFieldLeak") ImageAsyncLoad obj = new ImageAsyncLoad(itemsData[position].image){

            @Override
            protected void onPostExecute(Bitmap bmp) {
                super.onPostExecute(bmp);
                    viewHolder.imgViewIcon.setImageBitmap(bmp);
            }
        };

        obj.execute();

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleActivity.class);
                intent.putExtra("title", viewHolder.title.getText().toString());
                intent.putExtra("desc", itemsData[position].description.substring(itemsData[position].description.indexOf(">")+20));
                intent.putExtra("date", viewHolder.date.getText().toString());
                intent.putExtra("link", itemsData[position].link);
                intent.putExtra("image", itemsData[position].image);

                intent.putExtra("email", user_email);

                view.getContext().startActivity(intent);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView title, desc, date;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            card = itemLayoutView.findViewById(R.id.card_view);
            title = itemLayoutView.findViewById(R.id.title);
            desc = itemLayoutView.findViewById(R.id.desc);
            date = itemLayoutView.findViewById(R.id.date);
            imgViewIcon = itemLayoutView.findViewById(R.id.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}