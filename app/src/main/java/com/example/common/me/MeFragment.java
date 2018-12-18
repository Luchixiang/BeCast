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
import com.example.common.activities.AboutActivity;
import com.example.common.tab.MainActivity;

public class MeFragment extends Fragment {
    private Context mContext;

    public static MeFragment newInstance() {
        return new MeFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mContext = getActivity();
        initView(view);
        return view;
    }
    public void initView(View view)
    {
        View localView = view.findViewById(R.id.local);
        View historyView = view.findViewById(R.id.history);
        View aboutView = view.findViewById(R.id.about);
        View setView = view.findViewById(R.id.set);
        aboutView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, AboutActivity.class);
            mContext.startActivity(intent);
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("luuchixiang", "onCreate: "+"2");
    }

}
