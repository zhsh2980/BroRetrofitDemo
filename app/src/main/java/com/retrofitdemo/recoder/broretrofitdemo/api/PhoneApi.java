package com.retrofitdemo.recoder.broretrofitdemo.api;

/**
 * Created by zhangshan on 16/9/8 下午12:03.
 */

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 手机号相关的API
 * Created by Asia on 2016/3/24 0024.
 */
public class PhoneApi {

    /**
     * HOST地址
     */
    public static final String BASE_URL = "http://apis.baidu.com";
    /**
     * 开发者Key
     */
    public static final String API_KEY = "4d37ca6a4ee6981e61990cccf495abd2";

    /**
     * 获取PhoneApi实例
     * @return
     */
    public static PhoneApi getApi(){
        return ApiHolder.phoneApi;
    }

    static class ApiHolder{
        private static PhoneApi phoneApi = new PhoneApi();
    }

    private PhoneService service;

    private PhoneApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PhoneService.class);
    }

    /**
     * 获取PhoneService实例
     * @return
     */
    public PhoneService getService(){
        return service;
    }
}
