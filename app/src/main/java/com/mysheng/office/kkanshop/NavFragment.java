package com.mysheng.office.kkanshop;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.adapter.NavAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItem;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.NavModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by myaheng on 2018/9/27.
 */

public class NavFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView navRecyclerView;
    private NavAdapter mAdapter;
    private List<NavModel> modelslist=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            position=bundle.getInt(PARAM);
        }
        return inflater.inflate(R.layout.nav_recycler_view, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        modelslist.clear();
       for(int i=0;i<IndexTools.navIcon.length;i++){
           NavModel navModel=new NavModel();
           navModel.setNavTitle(IndexTools.navTitle[i]);
           navModel.setNavIcon(IndexTools.navIcon[i]);
           modelslist.add(navModel);
       }
        navRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new NavAdapter(KkanApplication.mContext,modelslist);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4);
        navRecyclerView.addItemDecoration(new DividerGridItem(getActivity()));
        navRecyclerView.setLayoutManager(gridLayoutManager);
        navRecyclerView.setAdapter(mAdapter);
    }




    public static NavFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        NavFragment fragment=new NavFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
