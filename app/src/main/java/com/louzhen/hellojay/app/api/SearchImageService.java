package com.louzhen.hellojay.app.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by louzhen on 16/10/15.
 */

interface SearchImageService {

    @GET("/search/acjson")
    Observable<SeachImageBean> RxContributors(@Query("tn") String tn,
                                              @Query("ipn") String ipn,
                                              @Query("fp") String fp,
                                              @Query("queryWord") String word1,
                                              @Query("word") String word2,
                                              @Query("pn") int pn,
                                              @Query("rn") int rn);
}
