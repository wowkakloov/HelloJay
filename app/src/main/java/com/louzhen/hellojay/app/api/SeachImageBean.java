package com.louzhen.hellojay.app.api;

import java.util.List;

/**
 * Created by louzhen on 16/10/15.
 */

public class SeachImageBean {

    private int gsm;

    public int getGsm() {
        return gsm;
    }

    public void setGsm(int gsm) {
        this.gsm = gsm;
    }

    private List<ImageBean> data;

    public List<ImageBean> getData() {
        return data;
    }

    public void setData(List<ImageBean> data) {
        this.data = data;
    }
}
