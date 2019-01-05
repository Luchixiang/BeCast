package com.example.common.tour;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.allen.library.RxHttpUtils;
import com.allen.library.observer.CommonObserver;
import com.example.common.ApiService;
import com.example.common.R;
import com.example.common.first.Bean;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RankAdapter rankAdapter;
    private ChooseAdapter chooseAdapter;
    private RelativeLayout searchPlace;
    private View rootView;
    private SearchView searchView;
    private Context context;
    private final List<Bean.Results> rankList = new ArrayList<>();
    private final List<Bean.Results> chosenList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    public static TourFragment newInstance() {
        return new TourFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_tour, container, false);
            context = getActivity();
            initView(rootView);
            contactInternet();
        }
        return rootView;
    }

    private void initView(View view) {
        RecyclerView rankRecycler = view.findViewById(R.id.tour_rank_recycler);
        RecyclerView chooseRecycler = view.findViewById(R.id.tour_fragment_choose_recycler);
        searchPlace = view.findViewById(R.id.search_area);
        searchView = view.findViewById(R.id.search_view);
        searchPlace.setOnClickListener(v -> searchPlace.setVisibility(View.GONE));
        searchView.setOnCloseListener(() -> {
            searchPlace.setVisibility(View.GONE);
            return true;
        });
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchPlace.setVisibility(View.GONE);
                        Log.d("luchixiang", "onQueryTextSubmit: " + query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );
        ImageView find = view.findViewById(R.id.tour_find);
        find.setOnClickListener(v -> {
            replaceSearchArea();
            searchView.setIconified(false);
        });
        LinearLayoutManager rankManager = new LinearLayoutManager(context);
        rankRecycler.setLayoutManager(rankManager);
        LinearLayoutManager chooseManager = new LinearLayoutManager(context);
        chooseManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        chooseRecycler.setLayoutManager(chooseManager);
        rankAdapter = new RankAdapter(rankList, context);
        rankRecycler.setAdapter(rankAdapter);
        chooseAdapter = new ChooseAdapter(chosenList, context, chooseRecycler);
        chooseRecycler.setAdapter(chooseAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void replaceSearchArea() {
        searchPlace.setVisibility(View.VISIBLE);
    }

    private void contactInternet() {
        RxHttpUtils.createApi(ApiService.class).getFox().compose(com.allen.library.interceptor.Transformer.switchSchedulers()).subscribe(new CommonObserver<Bean>() {
            @Override
            protected void onError(String s) {
                Log.d("luchixiang", "onError: ");
            }

            @Override
            protected void onSuccess(Bean bean) {
                chooseAdapter.listChanged(bean.getResults());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RxHttpUtils.createApi(ApiService.class).getQiNiuToken().compose(com.allen.library.interceptor.Transformer.switchSchedulers()).subscribe(new CommonObserver<Bean>() {
            @Override
            protected void onError(String s) {
                Log.d("luchixiang", "onError: ");
            }

            @Override
            protected void onSuccess(Bean bean) {
                rankAdapter.listChanged(bean.getResults());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        contactInternet();
    }
}