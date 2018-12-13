package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.adapter.ChatListViewAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.entity.ChatListModel;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;

import com.xiaomi.mimc.MIMCTokenFetcher;

import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatListViewActivity extends BaseActivity  implements MIMCTokenFetcher {
    private RecyclerView recyclerView;
    private TextView strTitle;
    private ChatListViewAdapter adapter;
    private ArrayList<ChatListModel> list = new ArrayList<>();
    private ImageButton btnBack;
    private SharedPreferencesUtils shareData;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_view);
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        initData();
        initView();
        initEvent();


    }
    /**
     * 初始化View
     */
    @Override
    protected void initView() {
        recyclerView =  findViewById(R.id.recycler_view);
        strTitle=findViewById(R.id.common_title);
        strTitle.setText("聊天聊表");
        adapter = new ChatListViewAdapter(list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ViewLineDivider(LinearLayoutManager.VERTICAL, 2, 0xFFCCCCCC));
        btnBack=findViewById(R.id.btn_back);
    }

    /**
     * 初始化event
     */
    @Override
    protected void initEvent() {
        btnBack.setOnClickListener(this);
        /**
         * 传递出slidelayout中content和options两个布局所有view的点击事件，根据需要做判断
         */
        adapter.setOnDeleteViewClickListener(new ChatListViewAdapter.OnChatListViewClickListener() {
            @Override
            public void onChildClick(View view,int position) {
                String string="";
                switch (view.getId()){
                    case R.id.chatListItem:
                    case R.id.content:
                    case R.id.userImage:
                    case R.id.userIcon:
                    case R.id.userName:
                    case R.id.lastMsg:
                    case R.id.lastMsgData:
                        //Toast.makeText(getActivity(), list.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        Intent intent = new Intent(ChatListViewActivity.this, ChatActivity.class);
                        Bundle bundle=new Bundle();
                        //传递name参数为tinyphp
                        bundle.putString("sendUserName", list.get(position).getUserName());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case R.id.options_root:
                        string = "操作菜单点击";
                        Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_open:
                        TextView textView0 = (TextView) view;
                        string = textView0.getText().toString();
                        Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_delete:
                        adapter.removeData(position);
//                        TextView textView2 = (TextView) view;
//                        string = textView2.getText().toString();
//                        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
                        break;

                }
                //Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    private void initData() {
        Random random=new Random();
        ChatListModel model=new ChatListModel();
        model.setUserId("dm01");
        model.setUserName("张三");
        model.setLastMsgData(new Date());
        model.setLastMsg("你在干嘛？");
        int num=random.nextInt(10);
        model.setUnReadNum(Math.round(num));
        list.add(model);
        ChatListModel model1=new ChatListModel();
        model1.setUserId("tx01");
        model1.setUserName("医生");
        model1.setLastMsgData(new Date(System.currentTimeMillis()-2*60*1000+15*1000));
        model1.setLastMsg("我的吧？");
        int num1=random.nextInt(10)+1;
        model1.setUnReadNum(Math.round(num1));
        list.add(model1);
        ChatListModel model2=new ChatListModel();
        model2.setUserId("dm02");
        model2.setUserName("李四");
        model2.setLastMsgData(new Date(System.currentTimeMillis()-2*60*60*1000+5*1000));
        model2.setLastMsg("哦哦哦！");
        int num2=random.nextInt(10);
        model2.setUnReadNum(Math.round(num2));
        list.add(model2);
        ChatListModel model3=new ChatListModel();
        model3.setUserId("dm03");
        model3.setUserName("王五");
        model3.setLastMsgData(new Date(System.currentTimeMillis()-24*60*60*1000+55*1000));
        model3.setLastMsg("还好吧？");
        int num3=random.nextInt(10);
        model3.setUnReadNum(Math.round(num3));
        list.add(model3);
        ChatListModel model4=new ChatListModel();
        model4.setUserId("dm03");
        model4.setUserName("雾里看花，水中望月");
        model4.setLastMsgData(new Date(System.currentTimeMillis()-4*24*60*60*1000+33*1000));
        model4.setLastMsg("还好吧？");
        int num4=random.nextInt(10);
        model4.setUnReadNum(Math.round(num4));
        list.add(model4);
    }


    @Override
    public String fetchToken() throws Exception {
        String token="";
        String urlPath="https://mimc.chat.xiaomi.net/api/account/token";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("appId", "2882303761517808316");
        hashMap.put("appKey", "5491780810316");
        hashMap.put("appSecret", "3OOIj5pjUvyGmU0Nowctzw==");
        hashMap.put("appAccount", userId);
        JSONObject jsonParams = new JSONObject(hashMap);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, String.valueOf(jsonParams));
            Request request = new Request.Builder()
                    .url(urlPath)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String strJson=response.body().string();
            JSONObject jsonStr = new JSONObject(strJson);
            String code=jsonStr.getString("code");
            if ("200".equals(code)){
                JSONObject data= (JSONObject) jsonStr.get("data");
                token=data.getString("token");
            }



        return token;

    }
}