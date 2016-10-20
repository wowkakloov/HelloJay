package com.louzhen.hellojay.app.api;

import android.util.Log;

import com.louzhen.hellojay.app.biz.searchimage.RespCallback;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louzhen on 16/10/14.
 */

public class BaiduSearchMapApi {

    /**
     * queryWord, word : 搜索关键字
     * pn：第几页 从0开始
     * rn：每页个数
     * eg: "http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&fp=result&queryWord=%s&word=%s&pn=%d&rn=%d";
     */
    private static final String ORIGIN_URL = "http://image.baidu.com";


    public static void excute(String word, int page, final RespCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ORIGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        SearchImageService searchImageService = retrofit.create(SearchImageService.class);
        searchImageService.RxContributors("resultjson_com", "rj", "result", word, word, page, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SeachImageBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(BaiduSearchMapApi.class.getSimpleName(),"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(BaiduSearchMapApi.class.getSimpleName(),"onError : " + e.getMessage());
                        callback.onFailed("错误: " + e.getMessage());
                    }

                    @Override
                    public void onNext(SeachImageBean beans) {
                        Log.i(BaiduSearchMapApi.class.getSimpleName(),"onNext--> " + beans);
                        if (beans != null && beans.getData() != null && beans.getData().size() > 0) {
                            callback.onSuccess(beans.getData());
                        } else {
                            callback.onFailed("无数据");
                        }
                    }
                });
    }


}
