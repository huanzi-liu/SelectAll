package com.example.administrator.okhttp;

import com.example.administrator.okhttp.https.HttpsUtils;
import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.response.CommonFileCallback;
import com.example.administrator.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author HuanZi
 * @date 2017\12\9 0009
 * @explain 用来发送请求的工具类 包括请求的公共参数
 */
public class CommonOkHttpClient {

    private static final int TIME = 30;
    private static OkHttpClient okHttpClient;

    static {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpBuilder.connectTimeout(TIME, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);
        okHttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());

        okHttpClient = okHttpBuilder.build();

    }

    public static void post(Request request , DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(dataHandle));

    }

    public static void get(Request request ,DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(dataHandle));

    }

    public static void downloadFile(Request request,DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(dataHandle));

    }

}
