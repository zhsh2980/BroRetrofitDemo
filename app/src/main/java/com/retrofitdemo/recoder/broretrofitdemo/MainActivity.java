package com.retrofitdemo.recoder.broretrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.retrofitdemo.recoder.broretrofitdemo.Bean.PhoneBean;
import com.retrofitdemo.recoder.broretrofitdemo.api.PhoneApi;
import com.retrofitdemo.recoder.broretrofitdemo.api.PhoneService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://apis.baidu.com";
    private static final String API_KEY = "4d37ca6a4ee6981e61990cccf495abd2";
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.tv_phone_location)
    TextView tvPhoneLocation;
    private PhoneService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mService = PhoneApi.getApi().getService();

    }

    private void initData() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();

        //2.创建访问API的请求
        PhoneService service = retrofit.create(PhoneService.class);
        Call<PhoneBean> result = service.getResult(API_KEY, etPhoneNum.getText().toString().trim());

        //3.发送请求
        result.enqueue(new Callback<PhoneBean>() {
            @Override
            public void onResponse(Call<PhoneBean> call, Response<PhoneBean> response) {
                //4.处理结果
                if (response.isSuccessful()) {
                    PhoneBean.PhoneRetDataBean data = response.body().getRetData();
                    String s = data.getProvince() + data.getCity() + data.getSupplier();
                    tvPhoneLocation.setText(s + "");
                }
            }

            @Override
            public void onFailure(Call<PhoneBean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "获取错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btn_get_phone_location, R.id.btn_get_phone_location_rxjava})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_phone_location:

                initData();

                break;
            case R.id.btn_get_phone_location_rxjava:

                initDataFromRxjava();
                break;
        }
    }

    private void initDataFromRxjava() {

        mService.getPhoneResult(PhoneApi.API_KEY, etPhoneNum.getText().toString().trim())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Observer<PhoneBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PhoneBean bean) {

                       // if (bean != null && bean.getErrNum() == 0) {
                            PhoneBean.PhoneRetDataBean data = bean.getRetData();
                            String s = data.getProvince() + data.getCity() + data.getSupplier();
                            tvPhoneLocation.setText(s + "");
                       // }

                    }
                });


    }
}
