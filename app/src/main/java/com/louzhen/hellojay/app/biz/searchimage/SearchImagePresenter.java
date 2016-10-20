package com.louzhen.hellojay.app.biz.searchimage;

import com.louzhen.hellojay.app.api.BaiduSearchMapApi;
import com.louzhen.hellojay.app.api.ImageBean;
import com.louzhen.hellojay.app.mvp.IPresenter;

import java.util.List;

/**
 * Created by louzhen on 16/10/15.
 */

public class SearchImagePresenter implements IPresenter {

    private ISearchImageView iSearchImageView;
    private int mPage = 0;

    public SearchImagePresenter(ISearchImageView iView) {
        iSearchImageView = iView;
    }

    public void getData(String word, boolean isFirstPage) {
        if (isFirstPage) {
            mPage = 0;
        } else {
            mPage++;
        }
        BaiduSearchMapApi.excute(word, mPage, respCallback);
    }

    private RespCallback respCallback = new RespCallback() {
        @Override
        public void onSuccess(List<ImageBean> beanList) {
            iSearchImageView.update(beanList, mPage);
        }

        @Override
        public void onFailed(String msg) {
            iSearchImageView.onError(msg);
        }
    };

    @Override
    public void attach() {

    }

    @Override
    public void detached() {
        iSearchImageView = null;
    }
}
