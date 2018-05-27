package com.mysheng.office.kkanshop;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysheng.office.kkanshop.adapter.RecommendAdapter;
import com.mysheng.office.kkanshop.entity.RecommendBean;
import com.mysheng.office.kkanshop.entity.User;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.util.LoginAsyncTask;
import com.mysheng.office.kkanshop.util.MikyouCommonDialog;
import com.mysheng.office.kkanshop.util.ReadLoginData;
import com.mysheng.office.kkanshop.util.UtilDialog;
import com.mysheng.office.kkanshop.util.VolleyImage;
import com.mysheng.office.kkanshop.util.VolleyInterface;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;
import com.mysheng.office.kkanshop.view.ObservableScrollView;
import com.mysheng.office.kkanshop.zxing.common.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class PersonFragment extends Fragment implements View.OnClickListener {
	private LinearLayout line;
	private ImageView setting;
	private GridView gridView;
	private ObservableScrollView scrollView;
	private int imageHeight=150;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab04, container, false);

		initView(view);
		initEvent();
		return view;
	}

	private void initView(View view){
		line=view.findViewById(R.id.line);
		setting=view.findViewById(R.id.new_setting);
		line.setBackgroundColor(Color.argb( 0, 72, 183, 245));//AGB由相关工具获得，或者美工提供
		line.bringToFront();
		gridView=view.findViewById(R.id.gridView);
		List<RecommendBean> lists=new ArrayList<>();

		for(int i=0;i<31;i++){
			RecommendBean bean=new RecommendBean();
			bean.setName("这是测试数据，这是测试数据"+i);
			bean.setPrice("￥"+i+".00");
			lists.add(bean);
		}
		RecommendAdapter adapter=new RecommendAdapter(getActivity(),lists);
		gridView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(gridView);
		scrollView= view.findViewById(R.id.scrollView);
		scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y <= 0) {
					line.setBackgroundColor(Color.argb((int) 0, 72, 183, 245));//AGB由相关工具获得，或者美工提供
				} else if (y > 0 && y <= imageHeight) {
					Log.d("mys", "onScrollChanged: "+y);
					float scale = (float) y / imageHeight;
					float alpha = (255 * scale);
					// 只是layout背景透明
					line.setBackgroundColor(Color.argb((int) alpha, 72, 183, 245));
				} else {
					line.setBackgroundColor(Color.argb((int) 255, 72, 183, 245));
				}
			}
		});
	}
	private void initEvent(){
		setting.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.new_setting:
				startSettingActivity();
			break;
		}
	}

	private void startSettingActivity() {
		Intent intent = new Intent(getActivity(), SettingActivity.class);
		startActivity(intent);
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
//									personName.setText(user.getTrueName());
//									personCode.setText(user.getUserName());
//									textContent.setVisibility(View.VISIBLE);
//									btnLogin.setVisibility(View.GONE);
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
//                        readLoginData.exitLoginDate(getActivity());
//						personName.setText("");
//						personCode.setText("");
//						textContent.setVisibility(View.GONE);
//						btnLogin.setVisibility(View.VISIBLE);
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
		//getLoginMessage();
	}
	private void getLoginMessage(){
//		String user_name=readLoginData.getLoginDate(getActivity(),"TrueName");
//		String user_code=readLoginData.getLoginDate(getActivity(),"UserName");
//		if(user_name!=""&&user_code!=""){
//			personName.setText(user_name);
//			personCode.setText(user_code);
//			textContent.setVisibility(View.VISIBLE);
//			btnLogin.setVisibility(View.GONE);
//		}else{
//			personName.setText("");
//			personCode.setText("");
//			textContent.setVisibility(View.GONE);
//			btnLogin.setVisibility(View.VISIBLE);
//		}
	}
	public  void setListViewHeightBasedOnChildren(GridView listView) {
		// 获取listview的adapter
		RecommendAdapter listAdapter = (RecommendAdapter) listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// 固定列宽，有多少列
		int col = 3;//listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(5, 5);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight+200;
		// 设置margin
		//((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 扫描二维码/条码回传
		if (requestCode == CommonUtil.SCAN_RESULT && resultCode == RESULT_OK) {
			if (data != null) {
				String content = data.getStringExtra(Constant.CODED_CONTENT);
				Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if(requestCode==CommonUtil.SCAN_CODE){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				CommonUtil.StartScanCode(getActivity());
			} else {
				// Permission Denied
				Toast.makeText(getActivity(), "您已拒绝，请打开手机应用权限设置", Toast.LENGTH_SHORT).show();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
