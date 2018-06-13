package com.sammie.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sammie.httpbox.HttpUtils;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickGet(View view) {
        HttpUtils.with(AdResponse.class)
                .get()
                .url("/api/v1/advert/school")
                .cache(false)
                .execute(new IHttpCallBack<AdResponse>() {
                    @Override
                    public void onSuccess(AdResponse adResponse) {
                        showToast(adResponse.getMsg());
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void clickPost(View view) {
        HttpUtils.with(RegisterResponse.class)
                .post()
                .url("/api/v1/auth/register")
                .param("phone","15913901399")
                .param("password","qqqqqq")
                .param("code","123456")
                .cache(false)
                .execute(new IHttpCallBack<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse registerResponse) {
                        showToast(registerResponse.getMsg());
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
