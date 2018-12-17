package com.mysheng.office.kkanshop.util;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mysheng.office.kkanshop.KkanApplication;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

/**
 * Created by mysheng on 18/5/14.
 */
public class VolleyRequest
{
    public static StringRequest stringRequest;
    public static JsonObjectRequest jsonRequest;
    public static Context context;

    public static void stringRequestGet(Context context,String url, String tag, VolleyInterface vif)
    {

        KkanApplication.getHttpQueues().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.GET,url,vif.loadingListener(),vif.errorListener());
        stringRequest.setTag(tag);
        KkanApplication.getHttpQueues().add(stringRequest);
        // 不写也能执行
//        MyApplication.getHttpQueues().start();
    }

    public static void stringRequestPost(Context context,String url, String tag,final Map<String, String> params, VolleyInterface vif)
    {
        KkanApplication.getHttpQueues().cancelAll(tag);
        stringRequest = new StringRequest(POST,url,vif.loadingListener(),vif.errorListener())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        KkanApplication.getHttpQueues().add(stringRequest);
        // 不写也能执行
//        MyApplication.getHttpQueues().start();
    }
    public static void JsonRequestGet(String url,final String token, String tag, VolleyJsonInterface vif)
    {

        KkanApplication.getHttpQueues().cancelAll(tag);
        jsonRequest = new JsonObjectRequest(Request.Method.GET,url,vif.loadingJsonListener(),vif.errorListener()){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                if(token!=null){
                    headers.put("token",token);
                }
                return headers;
            }
        };
        jsonRequest.setTag(tag);
        KkanApplication.getHttpQueues().add(jsonRequest);
        // 不写也能执行
//        MyApplication.getHttpQueues().start();
    }
    public static void JsonRequestPost(String url, String tag, final String token, JSONObject params, VolleyJsonInterface vif)
    {
        KkanApplication.getHttpQueues().cancelAll(tag);
        jsonRequest = new JsonObjectRequest(POST,url,params,vif.loadingJsonListener(),vif.errorListener()){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                if(token!=null){
                    headers.put("token",token);
                }
                return headers;
            }
        };
        jsonRequest.setTag(tag);
        KkanApplication.getHttpQueues().add(jsonRequest);
        // 不写也能执行
//        MyApplication.getHttpQueues().start();
    }
}
