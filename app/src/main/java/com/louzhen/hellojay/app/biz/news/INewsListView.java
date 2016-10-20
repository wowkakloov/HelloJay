package com.louzhen.hellojay.app.biz.news;

import com.louzhen.hellojay.app.api.NeteaseNewBean;
import com.louzhen.hellojay.app.mvp.IView;

import java.util.List;

/**
 * Created by louzhen on 16/10/15.
 */

public interface INewsListView extends IView {

    void update(List<NeteaseNewBean> neteaseNewBeen, int page);

    void onError(String msg);
}
