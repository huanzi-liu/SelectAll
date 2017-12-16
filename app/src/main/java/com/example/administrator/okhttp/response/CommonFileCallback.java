package com.example.administrator.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.administrator.okhttp.exception.OkHttpException;
import com.example.administrator.okhttp.listener.DisposeDataHandle;
import com.example.administrator.okhttp.listener.DisposeDownLoadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author HuanZi
 * @date 2017\12\9 0009
 * @explain 处理文件下载的回调
 */
public class CommonFileCallback implements Callback {

    protected final int NETWORK_ERROR = -1;
    protected final int IO_ERROR = -2;
    protected final String EMPTY_MSG = "";

    private Handler handler;
    private static final int PROGRESS_MESSAGE = 0x01;
    private DisposeDownLoadListener listener;
    private String filePath;
    private int progress;
    File file = null;

    public CommonFileCallback(DisposeDataHandle handle) {

        this.listener = (DisposeDownLoadListener) handle.listener;
        this.filePath = handle.source;

        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        listener.onProgress((int) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(Call call, final IOException e) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {


        if (response != null) {
            file = handlerResponse(response);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    listener.onSuccess(file);
                } else {
                    listener.onFailure(new OkHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });


    }

    public File handlerResponse(Response response) {
        if (response == null) {
            return null;
        }
        InputStream is = null;
        File file = null;
        FileOutputStream fos = null;

        byte[] buffor = new byte[2048];
        int length = -1;
        int currentLength = 0;
        double sumLength = 0;
        try {

            file = new File(filePath);
            is = response.body().byteStream();
            fos = new FileOutputStream(file);
            sumLength = (double)response.body().contentLength();
            while ((length = is.read(buffor)) != -1) {
                fos.write(buffor, 0, length);
                currentLength += length;
                progress = (int)(currentLength / sumLength * 100);
                handler.obtainMessage(PROGRESS_MESSAGE, progress).sendToTarget();
            }

//            fos.flush();
            fos.close();
            is.close();
        } catch (Exception e) {
            file = null;
            e.printStackTrace();

        }

        return file;
    }
}
