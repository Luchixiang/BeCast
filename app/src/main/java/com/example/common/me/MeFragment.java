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
import android.widget.Button;

import com.example.common.R;
import com.example.common.tab.MainActivity;

public class MeFragment extends Fragment {
    public static MeFragment newInstance() {
        return new MeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Context  context = getActivity();
        Button button =view.findViewById(R.id.test_button);
        Button button2 =view.findViewById(R.id.test_button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.next");
                assert context != null;
                context.sendBroadcast(intent);
//                MainActivity.nextMusic();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.add");
                intent.putExtra("URL","https://sdvideos.s3.cn-north-1.amazonaws.com.cn/allnew2/%E6%9D%8E%E5%A2%9E%E7%83%88%2B%E6%85%A2%E6%80%A7%E8%85%B9%E6%B3%BB%E4%B8%8E%E2%80%9C%E7%BB%93%E8%82%A0%E7%82%8E.mp4");
                assert context != null;
                context.sendBroadcast(intent);
//                MainActivity.addMusic("https://sdvideos.s3.cn-north-1.amazonaws.com.cn/allnew2/%E6%9D%8E%E5%A2%9E%E7%83%88%2B%E6%85%A2%E6%80%A7%E8%85%B9%E6%B3%BB%E4%B8%8E%E2%80%9C%E7%BB%93%E8%82%A0%E7%82%8E.mp4");
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("luuchixiang", "onCreate: "+"2");
    }

}
