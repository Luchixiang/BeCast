package com.example.common.about;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.common.R;

import library.common.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView imageView = findViewById(R.id.about_back);
        imageView.setOnClickListener(v-> finish());
    }
}
