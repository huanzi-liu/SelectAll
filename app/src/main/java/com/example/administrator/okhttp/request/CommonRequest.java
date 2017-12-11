package com.example.administrator.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author HuanZi
 * @date 2017\12\5 0005
 * @explain 创建各种类型的请求
 */
public class CommonRequest {

    /**
     * get
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder sb = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()
                    ) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        return new Request.Builder().url(sb.substring(0, sb.length() - 1)).get().build();
    }

    /**
     * post
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder fbb = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()
                    ) {
                fbb.add(entry.getKey(), entry.getValue());
            }
        }

        FormBody fb = fbb.build();

        return new Request.Builder().url(url).post(fb).build();
    }


    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    /**
     * 文件上传
     * @param url
     * @param params
     * @return
     */
    public static Request CreateFileRequest(String url, RequestParams params) {

        MultipartBody.Builder mb = new MultipartBody.Builder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()
                    ) {
                if (entry.getValue() instanceof File) {
                    mb.addPart(Headers.of("Content-Disposition", "form-data;name=\"" + entry.getKey() + "\""), RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    mb.addPart(Headers.of("Content-Disposition", "foem-date;name=\"" + entry.getKey() + "\""), RequestBody.create(null, (String) entry.getValue()));

                }
            }
        }

        return new Request.Builder().url(url).post(mb.build()).build();
    }
}
