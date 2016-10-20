package com.louzhen.hellojay.app.biz.searchimage;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.louzhen.hellojay.app.R;
import com.louzhen.hellojay.app.biz.searchimage.widget.ZoomImageView;
import com.louzhen.hellojay.app.util.AppContext;

/**
 * Created by louzhen on 16/10/19.
 */

public class ImagePreviewActivity extends AppCompatActivity {

    private ZoomImageView zoomImageView;
    private String imageUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_image_layout);
        Intent intent = getIntent();
        if (intent != null) {
            imageUrl = intent.getStringExtra("url");
            Log.d("ImagePreviewActivity", "image url : " + imageUrl);
        }
        zoomImageView = (ZoomImageView) findViewById(R.id.image);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (imageUrl != null) {
            Glide.with(AppContext.appContext).load(imageUrl).into(zoomImageView);
        }
    }
}
