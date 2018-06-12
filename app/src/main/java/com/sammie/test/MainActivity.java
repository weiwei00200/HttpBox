package com.sammie.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sammie.httpbox.HttpConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOkHttp(View view) {
        HttpConfig httpConfig = new HttpConfig.HttpConfigBuilder("http://xxxx/")
                .timeout(15000)
                .retryOnFail(true)
                .retryTimes(2)
                .cacheEnable(false)
                .builder();

    }

    public void clickXUtil(View view) {

    }
}
