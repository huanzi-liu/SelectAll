package com.example.administrator.okhttp.exception;

/**
 * @author HuanZi
 * @date 2017\12\4 0004
 * @explain 自定义异常返回类
 */
public class OkHttpException extends Exception {

    private static final long serialVersionUID = 1L;

    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;

    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }

}
