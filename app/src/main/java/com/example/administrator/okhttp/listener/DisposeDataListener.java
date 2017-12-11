package com.example.administrator.okhttp.listener;

/**
 * @author HuanZi
 * @date 2017\12\4
 * @explain 业务逻辑层处理的地方
 */
public interface DisposeDataListener {

    /**
     * 请求成功回调事件处理
     * @param resObj
     */
    public void onSuccess(Object resObj);

    /**
     * 请求失败回调事件处理
     * @param resObj
     */
    public void onFailure(Object resObj);

}
