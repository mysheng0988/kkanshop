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

import java.util.ArrayList;

public class ChatListViewActivity extends Activity {
    private RecyclerView recyclerView;
    private TextView strTitle;
    private ChatListViewAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_view);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
        adapter.setOnDeleteViewClickListener(new ChatListViewAdapter.OnDeleteViewClickListener() {
            @Override
            public void onChildClick(View view) {
                String string = "";
                if (view.getId() == R.id.chatListItem) {
                   // TextView textView=view.findViewById(R.id.userName);
                   // string = textView.getText().toString();
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(ChatListViewActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.options_root) {
                    string = "操作菜单点击";
                    Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();
                } else if (view.getId() == R.id.tv_open) {
                    TextView textView = (TextView) view;
                    string = textView.getText().toString();
                    Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();

                } else if (view.getId() == R.id.tv_delete) {
                    TextView textView = (TextView) view;
                    string = textView.getText().toString();
                    Toast.makeText(ChatListViewActivity.this, string, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            list.add("测试" + i);
        }
    }
}