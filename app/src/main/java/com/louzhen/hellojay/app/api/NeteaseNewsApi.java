package com.louzhen.hellojay.app.api;

import android.util.Log;


import com.louzhen.hellojay.app.biz.news.RespCallback;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louzhen on 16/10/14.
 */

public class NeteaseNewsApi {

    /**
     * 头条新闻
     * 0:  从第几个开始，要是20的倍数，0，20，40...
     * 20: 每页个数
     * http://c.m.163.com/nc/article/headline/T1348647909107/%d-%d.html
     */
    public static final String ORIGIN_URL = "http://c.m.163.com";

    public static final String TYPE = "T1348647909107";

    public static void excute(int page, final RespCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ORIGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NeteaseNewsService neteaseNewsService = retrofit.create(NeteaseNewsService.class);
        neteaseNewsService.getNewsList(page * 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, List<NeteaseNewBean>>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(NeteaseNewsApi.class.getSimpleName(),"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(NeteaseNewsApi.class.getSimpleName(),"onError : " + e.getMessage());
                        callback.onFailed("错误: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Map<String, List<NeteaseNewBean>> beans) {
                        Log.i(NeteaseNewsApi.class.getSimpleName(),"onNext--> " + beans);
                        if (beans != null && beans.get(TYPE) != null ) {
                            callback.onSuccess(beans.get(TYPE));
                        } else {
                            callback.onFailed("无数据");
                        }
                    }
                });
    }

}
