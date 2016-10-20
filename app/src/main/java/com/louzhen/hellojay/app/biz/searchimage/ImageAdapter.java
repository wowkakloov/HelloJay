package com.louzhen.hellojay.app.biz.searchimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.louzhen.hellojay.app.R;
import com.louzhen.hellojay.app.api.ImageBean;

import java.util.Iterator;
import java.util.List;

/**
 * Created by louzhen on 16/10/14.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<ImageBean> mImages;
    private ItemClickListener mItemClickListener;

    public ImageAdapter(Context context) {
        mContext = context;
    }

    public void setImages(List<ImageBean> images) {
        mImages = check(images);
    }

    public void addImages(List<ImageBean> images) {
        if (mImages != null) {
            mImages.addAll(check(images));
        } else {
            mImages = check(images);
        }
    }

    private List<ImageBean> check(List<ImageBean> images) {
        if (images == null || images.size() == 0) return images;
        Iterator<ImageBean> imageBeanIterable = images.iterator();
        while (imageBeanIterable.hasNext()) {
            ImageBean imageBean = imageBeanIterable.next();
            if (TextUtils.isEmpty(imageBean.getMiddleURL())) {
                imageBeanIterable.remove();
            }
        }
        return images;
    }

    public void setItemClickListener(ItemClickListener l) {
        mItemClickListener = l;
    }

    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageViewHolder imageViewHolder = new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.search_image_item, parent, false));
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if (mImages != null) {
            final ImageBean imageBean = mImages.get(position);
//            if (position % 2 == 0) {
//                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
//                layoutParams.height = 500;
//                holder.itemView.setLayoutParams(layoutParams);
//            } else {
//                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
//                layoutParams.height = 600;
//                holder.itemView.setLayoutParams(layoutParams);
//            }
            Glide.with(mContext).load(imageBean.getMiddleURL()).into(holder.imageView);
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onClick(v, position, imageBean.getMiddleURL());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mImages != null ? mImages.size() : 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
