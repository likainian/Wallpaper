package com.qf.administrator.wallpaper.asynctask;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 16/10/26.
 */

public class BeansRequest<T> extends Request<T> {
    private Response.Listener listener;
    private String json;
    private Class<T> clazz;

    public BeansRequest(int method, String url, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
    }
    public BeansRequest(String url,Class<T> clazz, Response.Listener listener,Response.ErrorListener errorListener) {
        super(Method.GET,url,errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }
    public BeansRequest(int method, String url,Class<T> clazz, Response.Listener listener,Response.ErrorListener errorListener) {
        super(method, url,errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        T beans = new Gson().fromJson(json, clazz);

        return Response.success(beans,HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        if(listener !=null){
            listener.onResponse(response);
        }
    }
}
