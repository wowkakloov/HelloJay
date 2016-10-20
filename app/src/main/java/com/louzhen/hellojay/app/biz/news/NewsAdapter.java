package com.louzhen.hellojay.app.biz.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.louzhen.hellojay.app.R;
import com.louzhen.hellojay.app.api.NeteaseNewBean;

import java.util.List;

/**
 * Created by louzhen on 16/10/19.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private List<NeteaseNewBean> mNews;
    private ItemClickListener mItemClickListener;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    public void setNews(List<NeteaseNewBean> news) {
        mNews = news;
    }

    public void addNews(List<NeteaseNewBean> news) {
        if (mNews != null) {
            mNews.addAll(news);
        } else {
            mNews = news;
        }
    }

    public void setItemClickListener(ItemClickListener l) {
        mItemClickListener = l;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsAdapter.NewsViewHolder newsViewHolder = new NewsAdapter.NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false));
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, final int position) {
        if (mNews != null) {
            final NeteaseNewBean newBean = mNews.get(position);
            Glide.with(mContext).load(newBean.getImgsrc()).into(holder.imageView);
            holder.title.setText(newBean.getTitle());
            holder.desc.setText(newBean.getDigest());
            holder.time.setText(newBean.getLmodify());
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onClick(v, position, newBean.getUrl());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNews != null ? mNews.size() : 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title, desc, time;

        public NewsViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
