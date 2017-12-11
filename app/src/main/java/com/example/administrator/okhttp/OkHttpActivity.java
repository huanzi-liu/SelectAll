package com.example.administrator.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.listener.DisposeDataListener;
import com.example.administrator.okhttp.request.CommonRequest;
import com.example.administrator.okhttp.request.RequestParams;
import com.example.administrator.selectall.R;

import java.io.IOException;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv)
    ImageView iv;

    String url = "https://www.baidu.com";
    String imgUrl = "https://publicobject.com/content/images/2014/Apr/butters_2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_get, R.id.tv_post,R.id.tv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get:
                getRequest();
                break;
            case R.id.tv_post:
                postRequest();
                break;
            case R.id.tv_download:
                downloadFileRequest();
                break;
        }
    }

    private void getRequest(){

        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object resObj) {
                tv2.setText(resObj.toString());
            }

            @Override
            public void onFailure(Object resObj) {

            }
        }));

    }

    private void postRequest(){

        RequestParams requestParams = new RequestParams();
        requestParams.put("name","123");
        requestParams.put("password","123");

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,requestParams),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object resObj) {
                tv2.setText(resObj.toString());
            }

            @Override
            public void onFailure(Object resObj) {

            }
        }));

    }

    private void downloadFileRequest(){
//        if (){
//
//        }

    }


   /* private void getRequest() {

        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //在非UI线程中 调用run操作ui
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText(res);
                    }
                });
            }
        });


    }*/

    /*private void postRequest() {

        OkHttpClient oc = new OkHttpClient();
        FormBody.Builder fbb = new FormBody.Builder();
        fbb.add("name", "123");
        fbb.add("sex", "1");
        Request request = new Request.Builder().url(url).post(fbb.build()).build();
        Call call = oc.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv2.setText(res);
                    }
                });
            }
        });

    }*/


}
