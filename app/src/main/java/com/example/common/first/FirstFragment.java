package com.example.common.first;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.example.common.ApiService;
import com.example.common.R;
import com.example.common.tour.Chosen;
import com.example.common.tour.Classify;
import com.example.common.tour.ClassifyAdapter;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends RxFragment {
    private static final String TAG = "luchixiang";
    public List<Top5.Results> list = new ArrayList<>();
    private View rootView;
    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
            rootView = inflater.inflate(R.layout.fragment_first, container, false);
            SwipeMenuRecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
            SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, position) -> {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                deleteItem.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.last,null));
                deleteItem.setText("shanchu");
                rightMenu.addMenuItem(deleteItem);
            };
            SwipeMenuItemClickListener mMenuItemClickListener = (menuBridge, position) -> {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                // 左侧还是右侧菜单：
                int direction = menuBridge.getDirection();
                // 菜单在Item中的Position：
                int menuPosition = menuBridge.getPosition();
                Toast.makeText(context,"asas",Toast.LENGTH_LONG).show();
            };
            recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
            recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            FirstFragmentAdapter adapter = new FirstFragmentAdapter(context, list, recyclerView);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
                            adapter.listChanger(data.getResults());
                            adapter.notifyDataSetChanged();
                            Log.d("luchixiang", "onSuccess: "+data.getResults().size());
                        }
                    });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
