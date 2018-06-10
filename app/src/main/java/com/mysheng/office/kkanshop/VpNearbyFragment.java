package com.mysheng.office.kkanshop;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VpNearbyFragment extends Fragment{
    private String mTitle;
    private static String STR_TITLE="title";
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            mTitle=bundle.getString(STR_TITLE);
        }
        TextView textView=new TextView(getActivity());
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
    public static VpNearbyFragment getInstance(String title){
        Bundle bundle=new Bundle();
        bundle.putString(STR_TITLE,title);
        VpNearbyFragment fragment=new VpNearbyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
