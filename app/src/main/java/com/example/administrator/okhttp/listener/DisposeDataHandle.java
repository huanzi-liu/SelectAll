package com.example.administrator.okhttp.listener;

/**
 * @author HuanZi
 * @date 2017\12\5 0005
 * @explain
 */
public class DisposeDataHandle {

    public DisposeDataListener listener = null;
    public Class<?> aClass = null;
    public String source = null;

    public DisposeDataHandle(DisposeDataListener disposeDataListener){
        this.listener = disposeDataListener;

    }

    public DisposeDataHandle(DisposeDataListener disposeDataListener ,Class<?> aClass){
        this.listener = disposeDataListener;
        this.aClass = aClass;

    }

    public DisposeDataHandle(DisposeDataListener disposeDataListener,String source){
        this.listener = disposeDataListener;
        this.source = source;

    }

}
