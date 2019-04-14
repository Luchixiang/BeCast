package com.example.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import com.example.common.base.BaseActivity;
import com.example.common.customizeview.HonrizonViewPager;
import com.example.common.interfaces.PlayerListener;
import com.example.common.model.Model;
import com.example.common.single.Single;
import com.example.common.tab.FragmentGenerator;
import com.example.common.tab.PageAdapter;
import com.example.common.utils.PlayerViewFold;
import com.example.common.utils.PlayerViewUnFold;
import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PlayerListener, OnPlayerEventListener {
    private PlayerViewFold playerViewFold;
    private SeekBar foldseekBar;
    private PlayerViewUnFold playerViewUnFold;
    private double transtionY2;
    private static boolean isFold = true;
    private HonrizonViewPager viewPager;
    private TabLayout tabLayout;
    private static final String TAG = "luchixiang";
    private List<SongInfo> songInfoList = new ArrayList<>();
    private TimerTaskManager mTimerTask;
    private ObjectAnimator mroutineImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
        MusicManager.getInstance().addPlayerEventListener(this);
        mTimerTask.setUpdateProgressTask(() -> {
            long position = MusicManager.getInstance().getPlayingPosition();
            long duration = MusicManager.getInstance().getDuration();
            if (foldseekBar.getMax() != duration) foldseekBar.setMax((int) duration);
            foldseekBar.setProgress((int) position);
        });
        changeSeekbar(foldseekBar);
    }

    public void initView() {
        MediaSessionConnection.getInstance().connect();
        mTimerTask = new TimerTaskManager();
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        playerViewFold = findViewById(R.id.player_views);
        playerViewFold.registerListener(this);
        playerViewUnFold = findViewById(R.id.player_views_unfold);
        playerViewUnFold.registerListener(this);
        foldseekBar = playerViewFold.getSeekbar();
        transtionY2 = playerViewUnFold.getTranslationY();
        mroutineImg = ObjectAnimator.ofFloat(playerViewFold.getPlayerImg(),"rotation",0f,360f);
        mroutineImg.setDuration(7000);
        mroutineImg.setInterpolator(new LinearInterpolator());
        mroutineImg.setRepeatCount(-1);
        mroutineImg.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaSessionConnection.getInstance().disconnect();
        playerViewUnFold.unRegisterListener(this);
        playerViewFold.unRegisterListener(this);
        MusicManager.getInstance().removePlayerEventListener(this);
        mTimerTask.removeUpdateProgressTask();
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
                    changeSeekbar(foldseekBar);
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
                    changeSeekbar(foldseekBar);
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
    public void addSong(SongInfo songInfo) {
        List<SongInfo> temp = MusicManager.getInstance().getPlayList();
        int current = MusicManager.getInstance().getNowPlayingIndex();
        temp.add(current, songInfo);
        MusicManager.getInstance().updatePlayList(temp);
    }


    @Override
    public void pause() {
        if (!MusicManager.getInstance().isPaused()) {
            MusicManager.getInstance().pauseMusic();
        } else {
            MusicManager.getInstance().playMusic();
        }
    }

    @Override
    public void lastSong() {
        MusicManager.getInstance().skipToPrevious();
    }

    @Override
    public void nextSong() {
        MusicManager.getInstance().skipToNext();
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

    public void changeTitle(SongInfo songInfo) {
        playerViewFold.setView(songInfo);
        playerViewUnFold.setView(songInfo);
    }


    public void storeInformation() {
        SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
        Single single = new Single();
        single.changeFromSongInfo(songInfo);
        Log.d("add", "storeInformation: " + single.getTitle());
        Model.getInstance(getApplication()).addHistory(single);
    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        mroutineImg.resume();
    }

    @Override
    public void onPlayerStart() {
        SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
        changeTitle(songInfo);
        storeInformation();
        mTimerTask.startToUpdateProgress();
        mroutineImg.resume();
    }

    @Override
    public void onPlayerPause() {
        mTimerTask.stopToUpdateProgress();
        mroutineImg.pause();
    }

    @Override
    public void onPlayerStop() {

    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {

    }

    @Override
    public void onBuffering() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {

    }

    public void changeSeekbar(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicManager.getInstance().seekTo((long) seekBar.getProgress());
            }
        });
    }
}

