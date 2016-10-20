package com.louzhen.hellojay.app.biz.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.louzhen.hellojay.app.R;

/**
 * Created by louzhen on 16/10/19.
 */

public class NewDetailActivity extends AppCompatActivity {

    private WebView webView;
    private String webUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_detail_layout);
        webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        if (intent != null) {
            webUrl = intent.getStringExtra("url");
            Log.d("NewDetailActivity", "web url : " + webUrl);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (webUrl != null) {
            webView.loadUrl(webUrl);
        }
    }
}
