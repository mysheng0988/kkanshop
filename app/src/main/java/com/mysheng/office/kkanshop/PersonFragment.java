package com.mysheng.office.kkanshop;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysheng.office.kkanshop.entity.User;
import com.mysheng.office.kkanshop.util.LoginAsyncTask;
import com.mysheng.office.kkanshop.util.MikyouCommonDialog;
import com.mysheng.office.kkanshop.util.ReadLoginData;
import com.mysheng.office.kkanshop.util.UtilDialog;
import com.mysheng.office.kkanshop.util.VolleyInterface;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class PersonFragment extends Fragment implements View.OnClickListener {
	private ReadLoginData readLoginData=new ReadLoginData();
	private TextView btnLogin;
	private TextView personName;
	private TextView personCode;
	private LinearLayout textContent;
	private LinearLayout message;
	private LinearLayout ipSetting;
	private LinearLayout ipFlow;
	private LinearLayout updatePassword;
	private LinearLayout switchUser;
	private LinearLayout loginExit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab04, container, false);
		initView(view);
		initEvent();
		//VolleyPost();
		//VolleyJsonPost();
		return view;
	}

	private void initView(View view){
		btnLogin=view.findViewById(R.id.login_btn);
		textContent=view.findViewById(R.id.textContent);
		personName=view.findViewById(R.id.person_name);
		personCode=view.findViewById(R.id.person_code);
		message=view.findViewById(R.id.message);
		ipSetting=view.findViewById(R.id.ip_setting);
		ipFlow=view.findViewById(R.id.ip_flow);
		updatePassword=view.findViewById(R.id.update_password);
		switchUser=view.findViewById(R.id.switch_user);
		loginExit=view.findViewById(R.id.login_exit);
	}
	private void initEvent(){
		btnLogin.setOnClickListener(this);
		message.setOnClickListener(this);
		ipSetting.setOnClickListener(this);
		ipFlow.setOnClickListener(this);
		updatePassword.setOnClickListener(this);
		switchUser.setOnClickListener(this);
		loginExit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_btn:
				dialog(getActivity(),"登录账户");
				break;
			case R.id.message:
				VolleyJsonPost();
				break;
			case R.id.ip_setting:
				VolleyPost();
				//UtilDialog.dialogIPSetting(getActivity(),"设置IP地址");
				break;
			case R.id.ip_flow:
				UtilDialog.dialogIPFlow(getActivity(),"流程图IP地址设置");
				break;
			case R.id.update_password:
				UtilDialog.dialogUpdate(getActivity(),"修改密码");
				break;
			case R.id.switch_user:
				dialog(getActivity(),"切换账户");
				break;
			case R.id.login_exit:
				dialogExit(getActivity());
				break;
		}
	}
	private void VolleyGet(){
		String url="http://apis.juhe.cn/mobile/get?phone=15701570988&key=335adcc4e891ba4e4be6d7534fd54c5d";
		VolleyRequest.stringRequestGet(getActivity(),url,"login",new VolleyInterface(getActivity(), VolleyInterface.listener, VolleyInterface.errorListener) {
			@Override
			public void onSuccess(String result) {
					Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(VolleyError error) {
				Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
			}
		});
	}
	private void VolleyPost(){
		String url="http://apis.juhe.cn/mobile/get?";
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("phone", "15701570988");
		hashMap.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
		//JSONObject jsonParams = new JSONObject(hashMap);
		VolleyRequest.stringRequestPost(getActivity(), url, "login",  hashMap, new VolleyInterface(getActivity(), VolleyInterface.listener, VolleyInterface.errorListener) {
			@Override
			public void onSuccess(String result) {
				Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(VolleyError error) {
				Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
			}
		});
	}

	private void VolleyJsonPost(){
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("goodsType", "1");
		hashMap.put("sellerNum", "20180327666");
		hashMap.put("goodsName", "绿箭口香糖2");
		hashMap.put("price", "1.5");
		hashMap.put("oldPrice", "2");
		JSONObject jsonParams = new JSONObject(hashMap);
		String url = "http://192.168.1.22:9090/office/goods/addGoods";
		VolleyRequest.JsonRequestPost(url,"json",jsonParams,new VolleyJsonInterface(getActivity(), VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener) {
			@Override
			public void onSuccess(JSONObject result) {
				Toast.makeText(getActivity(),result.toString(),Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(VolleyError error) {
				Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
			}
		});
	}

	/**
	 *  {

	 "goodsType": "1",
	 "sellerNum": "20180327666",
	 "goodsName": "绿箭口香糖2",
	 "goodsNum": null,
	 "price": 1.5,
	 "oldPrice": 2,
	 "goodsDescribe": "11111",
	 "norms": "个",
	 "imageUrl": "image/lvjian.png",
	 "repertory": 0,
	 "status": 0
	 }

	 */
	private void getJsonPost(){
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("goodsType", "1");
		hashMap.put("sellerNum", "20180327666");
		hashMap.put("goodsName", "绿箭口香糖2");
		hashMap.put("price", "1.5");
		hashMap.put("oldPrice", "2");
		JSONObject jsonParams = new JSONObject(hashMap);
		String url = "http://192.168.1.22:9090/office/goods/addGoods";
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,  jsonParams,
				new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response) {

                        Toast.makeText(getActivity(),response.toString(), Toast.LENGTH_SHORT).show();
					}
				},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();

					}


				}
		){

			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");

				return headers;
			}
		};


		request.setTag("jsonPost");
		KkanApplication.getHttpQueues().add(request);
	}
	public  void dialog(final Context context, final String title){//弹出登录框
		new MikyouCommonDialog(context, R.layout.login_edit_layout,title,"确定","取消")
				.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
					@Override
					public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
						EditText userID=customView.findViewById(R.id.user_id);
						EditText userPassword=customView.findViewById(R.id.user_password);
						String id= userID.getText().toString();
						String password=  userPassword.getText().toString();
						if("".equals(id)||"".equals(password)) {
							Toast.makeText(context,"用户名和密码不能为空！", Toast.LENGTH_LONG).show();
						}else{
							dialogInterface.dismiss();
							String path="http://39.104.72.213:80/api/api/accounts/account/login_user?username="+id +"&hashedPassword="+password;
							LoginAsyncTask loginAsyncTask=new LoginAsyncTask(context);
							loginAsyncTask.setCallBackAsyncTask(new LoginAsyncTask.CallBackAsyncTask() {
								@Override
								public void onCallBack(User user) {
									personName.setText(user.getTrueName());
									personCode.setText(user.getUserName());
									textContent.setVisibility(View.VISIBLE);
									btnLogin.setVisibility(View.GONE);
								}
							});
							loginAsyncTask.execute(path);
						}
					}

					@Override
					public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

					}
				}).showDialog();
	}
	public void dialogExit(final Context context){//确定退出
		new MikyouCommonDialog(context, "您确定退出登录吗？","退出登录","确定","取消")
				.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
					@Override
					public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
						dialogInterface.dismiss();
                        readLoginData.exitLoginDate(getActivity());
						personName.setText("");
						personCode.setText("");
						textContent.setVisibility(View.GONE);
						btnLogin.setVisibility(View.VISIBLE);
						Toast.makeText(context, "已退出登录", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {
						dialogInterface.dismiss();
						//Toast.makeText(SettingActivity.this, "取消", Toast.LENGTH_SHORT).show();
					}
				}).showDialog();
	}

	@Override
	public void onResume() {
		super.onResume();
		getLoginMessage();
	}
	private void getLoginMessage(){
		String user_name=readLoginData.getLoginDate(getActivity(),"TrueName");
		String user_code=readLoginData.getLoginDate(getActivity(),"UserName");
		if(user_name!=""&&user_code!=""){
			personName.setText(user_name);
			personCode.setText(user_code);
			textContent.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
		}else{
			personName.setText("");
			personCode.setText("");
			textContent.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
		}
	}
}
