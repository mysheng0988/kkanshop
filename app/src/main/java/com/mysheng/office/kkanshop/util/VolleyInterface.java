package com.mysheng.office.kkanshop.util;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


/**
 * Created by mysheng on 18/5/14.
 */
public abstract class VolleyInterface
{
    public Context context;
    public static Response.Listener<String> listener;

    public static Response.ErrorListener errorListener;

    public abstract void onSuccess(String result);
    public abstract  void onError(VolleyError error);



    public VolleyInterface (Context context,Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        this.context = context;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public Response.Listener<String> loadingListener()
    {
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess(response);
            }
        };
        return listener;
    }

    public Response.ErrorListener errorListener()
    {
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        };
        return errorListener;
    }


}
