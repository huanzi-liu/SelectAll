package com.example.administrator.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.administrator.okhttp.exception.OkHttpException;
import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author HuanZi
 * @date 2017\12\5 0005
 * @explain 处理Json的回调
 */
public class CommonJsonCallback implements Callback {

    protected final String RESULT_CODE = "code";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR = "error";
    protected final String EMPTY = "";
    protected final String COOKIE = "Set-cookie";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener listener;
    private Class<?> aClass;
    private Handler ahandler;

    private Gson gson = new Gson();

    private static final String TAG = "CommonJsonCallback";

    public CommonJsonCallback(DisposeDataHandle handle){
        listener = handle.listener;
        aClass = handle.aClass;
        ahandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {

        ahandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(e);
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        ahandler.post(new Runnable() {
            @Override
            public void run() {
                handlerResponse(result);
            }
        });
    }

    public void handlerResponse(String result){
        Log.e(TAG, "handlerResponse: "+result );
        if (result == null || result.equals("")){
            listener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY));
        }
        listener.onSuccess(result);
        /*try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(RESULT_CODE)){

                if (jsonObject.optInt(RESULT_CODE) == RESULT_CODE_VALUE){

                    if (aClass == null){
                        listener.onSuccess(jsonObject);
                    }else{
                        Object object = gson.fromJson(result, aClass);

                        if (object != null){
                            listener.onSuccess(object);
                        }else{
                            listener.onFailure(new OkHttpException(JSON_ERROR,EMPTY));
                        }

                    }

                }else{

                    if (jsonObject.has(ERROR)){
                        listener.onFailure(new OkHttpException(jsonObject.optInt(RESULT_CODE),jsonObject.optString(ERROR)));
                    }else{
                        listener.onFailure(new OkHttpException(jsonObject.optInt(RESULT_CODE),EMPTY));
                    }

                }

            }else{

                if(jsonObject.has(ERROR)){
                    listener.onFailure(new OkHttpException(OTHER_ERROR,jsonObject.optString(ERROR)));
                }

            }

        }catch (Exception e){
            listener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
            e.printStackTrace();
        }*/
    }
}
