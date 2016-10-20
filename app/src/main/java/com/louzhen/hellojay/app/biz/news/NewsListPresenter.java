package com.louzhen.hellojay.app.biz.news;

import com.louzhen.hellojay.app.api.BaiduSearchMapApi;
import com.louzhen.hellojay.app.api.NeteaseNewBean;
import com.louzhen.hellojay.app.api.NeteaseNewsApi;
import com.louzhen.hellojay.app.mvp.IPresenter;

import java.util.List;

/**
 * Created by louzhen on 16/10/19.
 */

public class NewsListPresenter implements IPresenter {

    private INewsListView mINewsListView;
    private int mPage = 0;

    public NewsListPresenter(INewsListView iNewsListView) {
        this.mINewsListView = iNewsListView;
    }

    public void getData(boolean isFirstPage) {
        if (isFirstPage) {
            mPage = 0;
        } else {
            mPage++;
        }
        NeteaseNewsApi.excute(mPage, respCallback);
    }

    RespCallback respCallback = new RespCallback() {
        @Override
        public void onSuccess(List<NeteaseNewBean> beanList) {
            mINewsListView.update(beanList, mPage);
        }

        @Override
        public void onFailed(String msg) {
            mINewsListView.onError(msg);
        }
    };

    @Override
    public void attach() {

    }

    @Override
    public void detached() {
        mINewsListView = null;
    }
}
