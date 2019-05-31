package com.example.rss_app.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rss_app.R;
import com.example.rss_app.app.activity.ArticleActivity;
import com.example.rss_app.app.activity.NewsActivity;
import com.example.rss_app.app.model.Article;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Article[] itemsData;

    public NewsAdapter(Article[] itemsData) {
        this.itemsData = itemsData;
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.title.setText(itemsData[position].title);
        viewHolder.desc.setText(itemsData[position].description);
        viewHolder.date.setText(itemsData[position].date);
        viewHolder.imgViewIcon.setImageBitmap(itemsData[position].image);

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleActivity.class);
                intent.putExtra("title", viewHolder.title.getText().toString());
                intent.putExtra("desc", viewHolder.desc.getText().toString());
                intent.putExtra("date", viewHolder.date.getText().toString());

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