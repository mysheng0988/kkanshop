package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.MIMC.common.Base64Utils;
import com.mysheng.office.kkanshop.MIMC.common.UserManager;
import com.mysheng.office.kkanshop.adapter.ChatListViewAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.entity.ChatListModel;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.VolleyInterface;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;
import com.xiaomi.mimc.MIMCUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class ChatListViewActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView strTitle;
    private ChatListViewAdapter adapter;
    private ArrayList<ChatListModel> list = new ArrayList<>();
    private ImageButton btnBack;
    private SharedPreferencesUtils shareData;
    private String userId;
    private String token;
    private MIMCUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        user = UserManager.getInstance().newUser(userId);
        user.login();
        setContentView(R.layout.chat_list_view);
        getUserChatList();
        initView();
        initEvent();
        initData();


    }
    /**
     * 初始化View
     */
    @Override
    protected void initView() {
        recyclerView =  findViewById(R.id.recycler_view);
        strTitle=findViewById(R.id.common_title);
        strTitle.setText("聊天聊表");
        adapter = new ChatListViewAdapter(ChatListViewActivity.this,list);
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
                        bundle.putString("sendUserId", list.get(position).getUserId());
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
    private void getUserChatList(){

            token=user.getToken();
            String strUrl="https://mimc.chat.xiaomi.net/api/contact/";
            VolleyRequest.JsonRequestGet(strUrl,token,"userList",new VolleyJsonInterface(ChatListViewActivity.this,VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener){
                @Override
                public void onSuccess(JSONObject result) {
                    String code="",message="";
                    try {
                        code=result.getString("code");
                        message=result.getString("message");
                        JSONArray array=result.getJSONArray("data");

                        for (int i=0;i<array.length();i++){
                            JSONObject object=array.getJSONObject(i);
                           String userType=object.getString("userType");
                           String id=object.getString("id");
                           String name=object.getString("name");
                           long timestamp=object.getLong("timestamp");
                           String extra=object.getString("extra");
                            JSONObject lastMessage=object.getJSONObject("lastMessage");
                            String fromUuid=lastMessage.getString("fromUuid");
                            String fromAccount=lastMessage.getString("fromAccount");
                            String payload=lastMessage.getString("payload");
                            payload= Base64Utils.getFromBase64(payload);
                            String sequence=lastMessage.getString("sequence");
                            String bizType=lastMessage.getString("bizType");
                            JSONObject obj=new JSONObject(payload);
                            String content=obj.getString("content");
                            content=Base64Utils.getFromBase64(content);
                            ChatListModel model=new ChatListModel();
                            model.setUnReadNum(4);
                            model.setUserId(name);
                            model.setUserName(name);
                            model.setLastMsgData(timestamp);
                            model.setLastMsg(content);
                            list.add(model);

                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onError(VolleyError error) {

                }


            });
    }
    private void getChataUserList(){
        token=user.getToken();
        String strURL="https://mimc.chat.xiaomi.net/api/msg/p2p/queryOnSequence/";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toAccount", "dm01");
        hashMap.put("fromAccount", userId);
        hashMap.put("startSeq","154503794553568001");
        hashMap.put("stopSeq","154503955225968001");
        JSONObject jsonParams = new JSONObject(hashMap);
        VolleyRequest.JsonRequestPost(strURL,"Sequence",token,jsonParams,new VolleyJsonInterface(this, VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener) {
            @Override
            public void onSuccess(JSONObject result) {
                String code="",message="";

                try {
                    code=result.getString("code");
                    message=result.getString("message");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
    private void initData() {
        getUserChatList();
        getChataUserList();
    }



}