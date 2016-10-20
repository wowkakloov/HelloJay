package com.louzhen.hellojay.app;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.louzhen.hellojay.app.util.AppContext;

/**
 * Created by louzhen on 16/10/14.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();

    }

    private void initApp() {
        AppContext.appContext = this;
    }

}
