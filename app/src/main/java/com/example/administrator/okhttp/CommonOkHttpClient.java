package com.example.administrator.okhttp;

import android.annotation.SuppressLint;

import com.example.administrator.okhttp.https.Https;
import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.response.CommonFileCallback;
import com.example.administrator.okhttp.response.CommonJsonCallback;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

//    static {
//
//        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
//
//        okHttpBuilder.hostnameVerifier(new HostnameVerifier()
//        {
//            @Override
//            public boolean verify(String hostname, SSLSession session)
//            {
//                return true;
//            }
//        });
//
//        okHttpBuilder.connectTimeout(TIME, TimeUnit.SECONDS);
//        okHttpBuilder.readTimeout(TIME, TimeUnit.SECONDS);
//        okHttpBuilder.writeTimeout(TIME, TimeUnit.SECONDS);
//        okHttpBuilder.followRedirects(true);
//        okHttpBuilder.sslSocketFactory(createSSLSocketFactory());
//
//        okHttpClient = okHttpBuilder.build();
//
//
//    }

    static {

        Https.SSLParams ssl = Https.getSslSocketFactory(null, null, null);
        okHttpClient = new OkHttpClient.Builder()
        .hostnameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        })

        .connectTimeout(TIME, TimeUnit.SECONDS)
                .readTimeout(TIME, TimeUnit.SECONDS)
                .writeTimeout(TIME, TimeUnit.SECONDS)
                .followRedirects(true)
                .sslSocketFactory(ssl.sSLSocketFactory, ssl.trustManager)
                .build();

    }

    public static void post(Request request , DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(dataHandle));

    }

    public static void get(Request request ,DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(dataHandle));

    }

    public static Call downloadFile(Request request,DisposeDataHandle dataHandle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(dataHandle));
        return call;

    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
