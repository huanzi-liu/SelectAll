package com.example.administrator.okhttp;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.listener.DisposeDataListener;
import com.example.administrator.okhttp.listener.DisposeDownLoadListener;
import com.example.administrator.okhttp.request.CommonRequest;
import com.example.administrator.okhttp.request.RequestParams;
import com.example.administrator.selectall.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OkHttpActivity extends AppCompatActivity {

    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv)
    ImageView iv;

    String url = "https://www.baidu.com";
    String imgUrl = "https://publicobject.com/content/images/2014/Apr/butters_2.jpg";

    private static final String TAG = "OkHttpActivity";
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

        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,null),
                new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object resObj) {
                tv2.setText(resObj.toString());
                Log.e(TAG, "onSuccess: get" );
            }

            @Override
            public void onFailure(Object resObj) {
                Log.e(TAG, "onFailure: get" +resObj.toString());
            }
        }));

    }

    private void postRequest(){

        RequestParams requestParams = new RequestParams();
        requestParams.put("name","123");
        requestParams.put("password","123");

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,requestParams),
                new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object resObj) {
                tv2.setText(resObj.toString());
                Log.e(TAG, "onSuccess: post" );
            }

            @Override
            public void onFailure(Object resObj) {
                Log.e(TAG, "onFailure: post" +resObj.toString());
            }
        }));

    }

    private void downloadFileRequest(){

//        AndPermission.with(this)
//                .requestCode(10)
//                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .callback(this)
//                .start();
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(imgUrl,null),
                new DisposeDataHandle(new DisposeDownLoadListener(){
                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(Object resObj) {
                        iv.setImageBitmap(BitmapFactory.decodeFile(((File)resObj).getAbsolutePath()));
                        Log.e(TAG, "onSuccess: download" );
                    }

                    @Override
                    public void onFailure(Object resObj) {
                        Log.e(TAG, "onFailure: download"+resObj.toString());
                    }
                }, Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis()+".jpg"
//                        Environment.getDownloadCacheDirectory()+"/"+System.currentTimeMillis()+".jpg"
                ));

    }

//    private PermissionListener listener = new PermissionListener() {
//        @Override
//        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
//
//        }
//
//        @Override
//        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
//
//        }
//    };

    @PermissionYes(10)
    private void getLocationYes(List<String> deniedPermissions){
        Log.e(TAG, "getLocationYes: " );
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(imgUrl,null),
                new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object resObj) {
                iv.setImageBitmap(BitmapFactory.decodeFile(((File)resObj).getAbsolutePath()));
                Log.e(TAG, "onSuccess: download" );
            }

            @Override
            public void onFailure(Object resObj) {
                Log.e(TAG, "onFailure: download" );
            }
        }, Environment.getDownloadCacheDirectory().getAbsolutePath()+"/"+System.currentTimeMillis()+".jpg"));
    }

    @PermissionNo(10)
    private void getLocationNo(List<String> deniedPermissions){
        Log.e(TAG, "getLocationNo: " );
        if(AndPermission.hasAlwaysDeniedPermission(this,deniedPermissions)){
            AndPermission.defaultSettingDialog(this,10).show();
        }
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
