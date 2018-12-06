package com.example.common.first;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.example.common.ApiService;
import com.example.common.R;
import com.example.common.tour.Classify;
import com.example.common.tour.ClassifyAdapter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends RxFragment {
    private static final String TAG = "luchixiang";
    public List<Classify> list = new ArrayList<>();

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ClassifyAdapter classifyAdapter = new ClassifyAdapter(context, list, recyclerView);
        recyclerView.setAdapter(classifyAdapter);
        classifyAdapter.notifyDataSetChanged();
//        RetrofitManager.
//                getInstance().
//                create(ApiService.class).getQiNiuToken().
//                compose(RxUtil.applySchedulers(FirstFragment.this))
//                .subscribe(new ResultObserver<Top5>() {
//                    @Override
//                    public void handlerResult(Top5 result) {
//                        boolean a = (result == null);
////                        Gson mgson = new Gson();
//                        Log.d("luchixiang", "handlerResult: " + a);
//                    }
//                });
        RxHttpUtils.createApi(ApiService.class)
                .getQiNiuToken()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<Top5> (){
                    @Override
                    protected void onError(String errorMsg) {
                        Log.d(TAG, "onError: "+1);
                    }
                    @Override
                    protected void onSuccess(Top5 data) {
                        Log.d("luchixiang", "onSuccess: "+data.getResults().size());
                    }
                });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
