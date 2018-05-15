package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


/**
 * Created by mysheng on 18/5/14.
 */
public abstract class VolleyJsonInterface
{
    public Context context;
    public static Response.Listener<JSONObject> mListener;
    public static Response.ErrorListener errorListener;


    public VolleyJsonInterface(Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        this.context = context;
        this.mListener = listener;
        this.errorListener = errorListener;
    }
    public abstract  void onError(VolleyError error);
    public abstract void onSuccess(JSONObject result);



    public Response.Listener<JSONObject> loadingJsonListener()
    {
        mListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onSuccess(response);
            }
        };
        return mListener;
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
