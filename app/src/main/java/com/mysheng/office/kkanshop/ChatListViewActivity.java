package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.adapter.ChatListViewAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.entity.ChatListModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ChatListViewActivity extends Activity {
    private RecyclerView recyclerView;
    private TextView strTitle;
    private ChatListViewAdapter adapter;
    private ArrayList<ChatListModel> list = new ArrayList<>();
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_view);
        initData();
        recyclerView =  findViewById(R.id.recycler_view);
        strTitle=findViewById(R.id.common_title);
        strTitle.setText("聊天聊表");
        adapter = new ChatListViewAdapter(list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ViewLineDivider(LinearLayoutManager.VERTICAL, 4, Color.WHITE));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        btnBack=findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}