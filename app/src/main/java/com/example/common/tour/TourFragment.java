package com.example.common.tour;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.common.R;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment {
    private RecyclerView rankRecycler;
    private RecyclerView chooseRecycler;
    RelativeLayout searchPlace;
    private View rootView;
    private SearchView searchView;
    private Context context;
    private List<Rank> rankList = new ArrayList<>();
    private List<Chosen> chosenList = new ArrayList<>();

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
        }else {
            rootView = inflater.inflate(R.layout.fragment_tour, container, false);
            context = getActivity();
            initView(rootView);}
        return rootView;
    }

        public void initView (View view){
            rankRecycler = view.findViewById(R.id.tour_rank_recycler);
            chooseRecycler = view.findViewById(R.id.tour_fragment_choose_recycler);
            searchPlace = view.findViewById(R.id.search_area);
            searchView = view.findViewById(R.id.search_view);
            searchPlace.setOnClickListener(v -> {
                searchPlace.setVisibility(View.GONE);
            });
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
            RankAdapter rankAdapter = new RankAdapter(rankList, rankRecycler, context);
            rankRecycler.setAdapter(rankAdapter);
            ChooseAdapter chooseAdapter = new ChooseAdapter(chosenList, context, chooseRecycler);
            chooseRecycler.setAdapter(chooseAdapter);
        }

        @Override
        public void onCreate (@Nullable Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        }

        public void replaceSearchArea () {
            searchPlace.setVisibility(View.VISIBLE);
        }
    }
