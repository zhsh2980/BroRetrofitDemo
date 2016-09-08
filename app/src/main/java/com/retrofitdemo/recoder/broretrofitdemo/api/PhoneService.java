package com.retrofitdemo.recoder.broretrofitdemo.api;

import com.retrofitdemo.recoder.broretrofitdemo.Bean.PhoneBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangshan on 16/9/8 上午10:44.
 */
public interface PhoneService {

    @GET("/apistore/mobilenumber/mobilenumber")
    Call<PhoneBean> getResult(@Header("apikey") String apikey , @Query("phone") String phone );

    @GET("/apistore/mobilenumber/mobilenumber")
    Observable<PhoneBean> getPhoneResult(@Header("apikey") String apikey , @Query("phone") String phone );


}
