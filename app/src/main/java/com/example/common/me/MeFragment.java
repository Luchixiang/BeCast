package com.example.common.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common.R;
import com.example.common.about.AboutActivity;
import com.example.common.history.HistoryActivity;
import com.example.common.local.LocalActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import library.common.base.BaseApplication;

public class MeFragment extends Fragment {
    private Context mContext;
    private View rootView;
    public static MeFragment newInstance() {
        return new MeFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        }else {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
            mContext = getActivity();
            initView(rootView);
        }
        return rootView;
    }
    public void initView(View view)
    {
        View localView = view.findViewById(R.id.local);
        View historyView = view.findViewById(R.id.history);
        View aboutView = view.findViewById(R.id.about);
        View setView = view.findViewById(R.id.set);
        ImageView myHead = view.findViewById(R.id.my_head_img);
        aboutView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, AboutActivity.class);
            mContext.startActivity(intent);
        });
        historyView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, HistoryActivity.class);
            mContext.startActivity(intent);
        });
        localView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LocalActivity.class);
            mContext.startActivity(intent);
        });
        myHead.setOnClickListener(v-> wxLogin());
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("luuchixiang", "onCreate: "+"2");
    }
    public void wxLogin() {
        if (!BaseApplication.mWxApi.isWXAppInstalled()) {
            Toast.makeText(mContext,"没有安装微信客户端",Toast.LENGTH_LONG).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        BaseApplication.mWxApi.sendReq(req);
    }
}
