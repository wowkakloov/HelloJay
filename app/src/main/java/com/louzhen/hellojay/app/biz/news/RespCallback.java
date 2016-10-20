package com.louzhen.hellojay.app.biz.news;

import com.louzhen.hellojay.app.api.NeteaseNewBean;

import java.util.List;

/**
 * Created by louzhen on 16/10/19.
 */

public interface RespCallback {

    void onSuccess(List<NeteaseNewBean> beanList);

    void onFailed(String msg);
}
