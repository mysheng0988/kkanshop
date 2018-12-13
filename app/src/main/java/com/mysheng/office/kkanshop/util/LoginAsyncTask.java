package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.mysheng.office.kkanshop.entity.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by myaheng on 2018/4/28.
 */

public class LoginAsyncTask extends AsyncTask<String,Void,UserModel> {
    private ReadLoginData readLoginData=new ReadLoginData();
    private Context context;
    //LoadTask的参数callBack
    private CallBackAsyncTask callBack;
    public LoginAsyncTask(Context context){
        this.context=context;
    }
    @Override
    protected UserModel doInBackground(String... strings) {
        return getJsonData(strings[0]);
    }
    @Override
    protected void onPostExecute(UserModel user) {
        super.onPostExecute(user);
        if(user!=null){
            readLoginData.setLoginData(context,"SessionKey",user.getSessionKey());
            readLoginData.setLoginData(context,"UserId",user.getUserId());
            readLoginData.setLoginData(context,"UserName",user.getUserName());
            readLoginData.setLoginData(context,"TrueName",user.getTrueName());
            readLoginData.setLoginData(context,"Sex",user.getSex());
            readLoginData.setLoginData(context,"Phone",user.getPhone());
            if(callBack!=null){
                callBack.onCallBack(user);
            }
        }else {
            Toast.makeText(context,"登录失败", Toast.LENGTH_SHORT).show();
        }


    }
    public void setCallBackAsyncTask(CallBackAsyncTask callBackAsyncTask){
        this.callBack = callBackAsyncTask;
    }

    //设置回调借口
    public interface CallBackAsyncTask{
        void onCallBack(UserModel user);
    }
    private UserModel getJsonData(String urlStr){
        UserModel user = null;
        try {
            URL url=new URL(urlStr);//实例化url：将获取网络数据的路径放到URL中。
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();//获取连接的对象
            httpURLConnection.setRequestMethod("POST");//设置请求的方式：1、get；2、post
            httpURLConnection.setConnectTimeout(5000);//请求连接超时的时间
            int code=httpURLConnection.getResponseCode(); //获取响应码的值
            if (code==200) {//判断：如果响应码等于200时，获取返回过来的是XML数据
                InputStream inputStream = httpURLConnection.getInputStream();

            String jsonString=readSteam(inputStream);
            JSONObject jo;

            jo=new JSONObject(jsonString);
            jo=jo.getJSONObject("result");
            String SessionKey=jo.getString("SessionKey");
            jo=jo.getJSONObject("LogonUser");

            String nn=jo.getString("Phone");
            user=new UserModel();
            user.setSessionKey(SessionKey);
            user.setPhone(jo.getString("Phone"));
            user.setSex(jo.getString("Sex"));
            user.setUserName(jo.getString("UserName"));
            user.setTrueName(jo.getString("TrueName"));
                user.setUserId(jo.getString("UserId"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
	}

	private String readSteam(InputStream is){
		InputStreamReader isr;
		String restult="";
		try {
			String line;
			isr=new InputStreamReader(is,"utf-8");
			BufferedReader br=new BufferedReader(isr);
			while ((line=br.readLine())!=null){
				restult+=line;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return restult;
	}
}
