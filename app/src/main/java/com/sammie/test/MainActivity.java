package com.sammie.test;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sammie.httpbox.HttpUtils;
import com.sammie.httpbox.model.HttpBoxException;
import com.sammie.httpbox.model.HttpConfig;
import com.sammie.httpbox.model.IHttpCallBack;
import com.sammie.httpbox.model.IHttpDownloadCallBack;
import com.sammie.httpbox.model.IHttpUploadCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickGet(View view) throws HttpBoxException {
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

    public void clickPost(View view) throws HttpBoxException {
        HttpUtils.with(RegisterResponse.class)
                .post()
                .url("/api/v1/auth/register")
                .param("phone", "15913901399")
                .param("password", "qqqqqq")
                .param("code", "123456")
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

    public void clickDownload(View view) throws HttpBoxException {
        final ProgressBar progressBar = findViewById(R.id.id_download_progress_bar);

        HttpUtils.with()
                .url("http://shouji.360tpcdn.com/180503/78ef176d24b7de2272bf8d88e9da5035/com.qihoo360.mobilesafe_260.apk")
                .downloadFileSavePath(getSDPath() + "/")
                .downloadFileName("360.apk")
                .download(new IHttpDownloadCallBack() {

                    @Override
                    public void process(int progress) {
                        progressBar.setProgress(progress);
                    }

                    @Override
                    public void onStartDownload() {
                        showToast("开始下载");
                    }

                    @Override
                    public void onDownloadSuccessful(String path) {

                    }

                    @Override
                    public void onDownloadFailed() {
                        showToast("下载失败");
                    }

                    @Override
                    public void onFinish() {
                        showToast("下载完成");
                    }
                });
    }

    public void clickUploadFile(View view) throws HttpBoxException {
        //不带参数上传文件
        HttpUtils.with()
                .url("http://xxxxxxx")
                .uploadFilePath(getSDPath() + "/360.apk")
                .upload(new IHttpUploadCallBack() {

                    @Override
                    public void onStartUpload() {
                        showToast("开始上传");
                    }

                    @Override
                    public void onUploadFailed() {
                        showToast("上传失败");
                    }

                    @Override
                    public void onUploadSuccessful() {
                        showToast("上传成功");
                    }

                    @Override
                    public void onFinish() {
                        showToast("上传完成");
                    }
                });
    }

    public void clickUploadFileWithParam(View view) throws HttpBoxException {
        //带参数上传文件
        HttpUtils.with()
                .url("http://xxxxxxx")
                .param("userName", "Sammie")
                .param("file", new File(getSDPath() + "/360.apk"))
                .upload(new IHttpUploadCallBack() {

                    @Override
                    public void onStartUpload() {
                        showToast("开始上传");
                    }

                    @Override
                    public void onUploadFailed() {
                        showToast("上传失败");
                    }

                    @Override
                    public void onUploadSuccessful() {
                        showToast("上传成功");
                    }

                    @Override
                    public void onFinish() {
                        showToast("上传完成");
                    }
                });
    }


    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            //如果SD卡存在，则获取跟目录
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
