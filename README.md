# 介绍
HttpBox是一款支持各种网络请求框架的切换，目前正在扩展中，现已支持OkHttp和xUtils的切换。
提供了以下功能：post，get，download，upload（带参数和不带参数）。

# 配置
#### 步骤1：
	implementation 'com.github.weiwei00200:HttpBox:1.0.0'
#### 步骤2：
	allprojects {
	   repositories {
	      maven { url "https://jitpack.io" }
	   }
	}

#### 初始化
在Application的onCreate中加入以下代码
###### 1.切换OkHttp框架
	HttpUtils.initHttpRequest(new OkHttpRequest(
        new HttpConfig.HttpConfigBuilder()
                .baseUrl(baseUrl)
                .timeout(15000)
                .retryOnFail(true)
                .builder()));//使用OkHttp
###### 2.切换OkHttp框架
	HttpUtils.initHttpRequest(new XUtilRequest(this,
         new HttpConfig.HttpConfigBuilder()
                 .baseUrl(baseUrl)
                 .timeout(15000)
                 .retryOnFail(true)
                 .builder()));//使用XUtil
###### 2.当然，你也可以参照OkHttpRequest或者XUtilRequest去定义自己的其他网络请求框架，然后自己扩展。
#### Get请求
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

#### Post请求
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

#### Download文件
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
#### Upload文件（不带参数）
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
#### Upload文件（带参数）
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


#### 混淆
	################### region for xUtils
	-keepattributes Signature,*Annotation*
	-keep public class org.xutils.** {
	    public protected *;
	}
	-keep public interface org.xutils.** {
	    public protected *;
	}
	-keepclassmembers class * extends org.xutils.** {
	    public protected *;
	}
	-keepclassmembers @org.xutils.db.annotation.* class * {*;}
	-keepclassmembers @org.xutils.http.annotation.* class * {*;}
	-keepclassmembers class * {
	    @org.xutils.view.annotation.Event <methods>;
	}
	#################### end region
	
	################### region for OkHttp
	-dontwarn okhttp3.**
	-dontwarn okio.**
	-dontwarn javax.annotation.**
	-dontwarn org.conscrypt.**
	# A resource is loaded with a relative path so the package of this class must be preserved.
	-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
	#################### end region
	
