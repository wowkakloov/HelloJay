package com.louzhen.hellojay.app.biz.searchimage;

import com.louzhen.hellojay.app.api.ImageBean;
import com.louzhen.hellojay.app.mvp.IView;

import java.util.List;

/**
 * Created by louzhen on 16/10/15.
 */

public interface ISearchImageView extends IView {

    void update(List<ImageBean> imageBeanList, int page);

    void onError(String msg);
}
