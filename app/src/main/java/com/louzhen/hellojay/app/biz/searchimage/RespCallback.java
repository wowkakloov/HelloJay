package com.louzhen.hellojay.app.biz.searchimage;

import com.louzhen.hellojay.app.api.ImageBean;

import java.util.List;

/**
 * Created by louzhen on 16/10/15.
 */

public interface RespCallback {

    void onSuccess(List<ImageBean> beanList);

    void onFailed(String msg);
}
