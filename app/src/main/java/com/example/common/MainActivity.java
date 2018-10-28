package com.example.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import library.common.base.BaseActivity;
import butterknife.BindView;
import library.common.http.result.Result;
import library.common.http.result.ResultObserver;
import library.common.RetrofitManager;
import library.common.util.RxUtil;

public class MainActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                RetrofitManager.
                        getInstance().
                        create(Icommon.class).getQiNiuToken().
                        compose(RxUtil.<Result<String>>applySchedulers(MainActivity.this))
                        .subscribe(new ResultObserver<String>() {
                    @Override
                    public void handlerResult(int string) {
                        Log.d("luchixiang", "sada" + string);
                    }
                });
            }
        });
    }
}

