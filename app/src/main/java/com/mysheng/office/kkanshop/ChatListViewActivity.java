package com.mysheng.office.kkanshop;
import android.content.Intent;
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
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
public class ChatListViewActivity extends BaseActivity {
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
    private void initData() {
        Random random=new Random();
        ChatListModel model=new ChatListModel();
        if(userId.equals("dm01")){
            model.setUserId("dm02");
            model.setUserName("张三");
            model.setLastMsgData(new Date());
            model.setLastMsg("你在干嘛？");
            int num=random.nextInt(10);
            model.setUnReadNum(Math.round(num));
        }else{
            model.setUserId("dm01");
            model.setUserName("张三");
            model.setLastMsgData(new Date());
            model.setLastMsg("你说呢？");
            int num=random.nextInt(10);
            model.setUnReadNum(Math.round(num));
        }
        list.add(model);
    }



}