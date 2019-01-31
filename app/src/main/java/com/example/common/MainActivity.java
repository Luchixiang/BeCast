package com.example.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.SeekBar;

import com.example.common.interfaces.BecastPlayerCallBack;
import com.example.common.interfaces.PlayerListener;
import com.example.common.model.Model;
import com.example.common.single.Single;
import com.example.common.tab.FragmentGenerator;
import com.example.common.tab.PageAdapter;
import com.example.common.utils.BecastPlayer;
import com.example.common.utils.HonrizonViewPager;
import com.example.common.utils.PlayerViewFold;
import com.example.common.utils.PlayerViewUnFold;

import library.common.base.BaseActivity;

public class MainActivity extends BaseActivity implements PlayerListener, BecastPlayerCallBack {
    private PlayerViewFold playerViewFold;
    private SeekBar foldseekBar;
    private PlayerViewUnFold playerViewUnFold;
    private double transtionY2;
    private static boolean isFold = true;
    private HonrizonViewPager viewPager;
    private TabLayout tabLayout;
    private static final String TAG = "luchixiang";
    BecastPlayer becastPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        playerViewFold = findViewById(R.id.player_views);
        playerViewFold.registerListener(this);
        playerViewUnFold = findViewById(R.id.player_views_unfold);
        playerViewUnFold.registerListener(this);
        foldseekBar = playerViewFold.getSeekbar();
        transtionY2 = playerViewUnFold.getTranslationY();
        initTab();
        becastPlayer = new BecastPlayer(this);
        Model.getInstance(getApplication()).getSingleList(singleList -> becastPlayer.initSingleList(singleList));
    }

    @Override
    protected void onStop() {
        super.onStop();
        becastPlayer.storeInformation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerViewUnFold.unRegisterListener(this);
        playerViewFold.unRegisterListener(this);
        becastPlayer.release();
    }

    //初始化tab
    private void initTab() {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), 3));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(FragmentGenerator.getTabView(this, i)));
        }
        viewPager.setCurrentItem(1);
    }

    private void changePlayer() {
        if (isFold) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(playerViewFold, "translationY", -30),
                    ObjectAnimator.ofFloat(playerViewFold, "alpha", 1, 0),
                    ObjectAnimator.ofFloat(playerViewUnFold, "translationY", -playerViewUnFold.getHeight() / 2),
                    ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 0, 1)
            );
            set.setDuration(500).addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    playerViewUnFold.setTranslationY((float) transtionY2);
                    playerViewUnFold.setVisibility(View.VISIBLE);
                    playerViewFold.setVisibility(View.GONE);
                    foldseekBar = playerViewUnFold.getSeekBar();
                    becastPlayer.initSeekBar(foldseekBar);
                    isFold = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        } else {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(playerViewFold, "translationY", 30),
                    ObjectAnimator.ofFloat(playerViewFold, "alpha", 0, 1),
                    ObjectAnimator.ofFloat(playerViewUnFold, "translationY", playerViewUnFold.getHeight() / 2),
                    ObjectAnimator.ofFloat(playerViewUnFold, "alpha", 1, 0)
            );
            set.setDuration(500).addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    playerViewFold.setVisibility(View.VISIBLE);
                    playerViewUnFold.setVisibility(View.GONE);
                    foldseekBar = playerViewFold.getSeekbar();
                    becastPlayer.initSeekBar(foldseekBar);
                    isFold = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        }
    }

    @Override
    public void Changer() {
        changePlayer();
    }

    @Override
    public void addSong(Single single) {
        becastPlayer.addMusic(single);
    }


    @Override
    public void pause() {
        becastPlayer.PlayOrPause();
    }

    @Override
    public void lastSong() {
        becastPlayer.lastMusic();
    }

    @Override
    public void nextSong() {
        becastPlayer.nextMusic();
    }

    @Override
    public void detail() {
//        Intent intent1 = new Intent(this, PlayerDetailsActivity.class);
//       startActivity(intent1);
    }

    @Override
    public void toRight() {
        if (viewPager.getCurrentItem() < 3) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void toLeft() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void changeTitle(Single single) {
        playerViewFold.setView(single);
        playerViewUnFold.setView(single);
    }

    @Override
    public SeekBar getSeekBar() {
        return foldseekBar;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void storeInformation(Single single) {
        Model.getInstance(getApplication()).addHistory(single);
    }
}

