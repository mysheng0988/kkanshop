package com.mysheng.office.kkanshop;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.adapter.ChatListViewAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.entity.ChatListModel;
import com.mysheng.office.kkanshop.listenter.MIMCUpdateChatMsg;
import com.mysheng.office.kkanshop.service.MIMCService;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.util.VolleyInterface;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;
import com.xiaomi.mimc.MIMCUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ChatListViewActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView strTitle;
    private ChatListViewAdapter adapter;
    private ArrayList<ChatListModel> list = new ArrayList<>();
    private ImageButton btnBack;
    private SharedPreferencesUtils shareData;

    private MIMCService mimcService;
    private String userId;
    private String token;
    private MIMCUser mimcUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        startMIMCService();
        setContentView(R.layout.chat_list_view);
        Log.e("mys", "onCreate: "+222);

        initView();
        initEvent();
        //initData();


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
    private boolean isBind = false;
    /**
     * 开启MIMC聊天
     */
    private void startMIMCService() {
        Intent intent = new Intent(this, MIMCService.class);
        isBind=bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }
    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("mys", "onServiceConnected: "+111);
            mimcService = ((MIMCService.MIMCBinder) service).getService();
            mimcUser=mimcService.getMimcUser();
            mimcUser.login();
            getUserChatList();
            mimcService.setUpdateChatMsg(new MIMCUpdateChatMsg() {
                @Override
                public void noticeNewMsg(ChatMsg chatMsg) {
                    nuReadNum++;
                    getUserChatList();
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private int nuReadNum=0;
    private void getUserChatList(){
        list.clear();
        token=mimcUser.getToken();
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
                        int msgType=obj.getInt("chatMsgType");
                        switch (msgType){
                            case Constant.MSG_TEXT:
                                content=new String(content);
                                break;
                            case Constant.MSG_IMAGE:
                                content="[图片]";
                                break;
                            case Constant.MSG_AUDIO:
                                content="[语音]";
                                break;
                            case Constant.MSG_VIDEO:
                                content="[视频]";
                                break;
                        }
                        ChatListModel model=new ChatListModel();
                        model.setUnReadNum(nuReadNum);
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
        token=mimcUser.getToken();
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
//    private void initData() {
//        getUserChatList();
//      //  getChataUserList();
//    }


    @Override
    protected void onResume() {
        Log.e("mys", "onPause: "+isBind );
        if(!isBind){
            startMIMCService();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        nuReadNum=0;
        unbindService(conn);
        isBind=false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}