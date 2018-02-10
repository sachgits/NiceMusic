package com.lzx.nicemusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.MusicInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.nicemusic.R;
import com.lzx.nicemusic.base.BaseMvpActivity;
import com.lzx.nicemusic.module.main.MainFragment;
import com.lzx.nicemusic.module.play.PlayingDetailActivity;
import com.lzx.nicemusic.module.search.SearchActivity;
import com.lzx.nicemusic.module.songlist.SongListFragment;
import com.lzx.nicemusic.utils.GlideUtil;



public class MainActivity extends BaseMvpActivity implements View.OnClickListener, OnPlayerEventListener {


    private ImageView mMusicCover, mBtnPlayList, mBtnPlayPause;
    private TextView mMusicName;
    private TextView mSingerName;
    private Intent intent;
    private MainFragment mMainFragment;
    private SongListFragment mSongListFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mMusicCover = findViewById(R.id.music_cover);
        mMusicName = findViewById(R.id.music_name);
        mSingerName = findViewById(R.id.singer_name);
        mBtnPlayList = findViewById(R.id.btn_play_list);
        mBtnPlayPause = findViewById(R.id.btn_play_pause);

        initFragment();

        mBtnPlayList.setOnClickListener(this);
        mBtnPlayPause.setOnClickListener(this);
        mMusicCover.setOnClickListener(this);
        mSingerName.setOnClickListener(this);
        mMusicName.setOnClickListener(this);
        MusicManager.get().addPlayerEventListener(this);
    }

    private void initFragment() {
        mMainFragment = (MainFragment) MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mMainFragment)
                .show(mMainFragment).commit();
    }

    public void switchFragment(String title) {
        mSongListFragment = (SongListFragment) SongListFragment.newInstance(title);
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.setCustomAnimations(
                R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out);
        trx.replace(R.id.container, mSongListFragment).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View view) {
        MusicInfo info = MusicManager.get().getCurrPlayingMusic();
        switch (view.getId()) {
            case R.id.ed_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_play_list:
                break;
            case R.id.btn_play_pause:
                if (info != null) {
                    if (MusicManager.isPlaying()) {
                        MusicManager.get().pauseMusic();
                    } else {
                        MusicManager.get().resumeMusic();
                    }
                }
                break;
            case R.id.music_cover:
            case R.id.singer_name:
            case R.id.music_name:
                if (info != null) {
                    PlayingDetailActivity.launch(this, info);
                }
                break;
        }
    }

    @Override
    public void onMusicSwitch(MusicInfo music) {
        GlideUtil.loadImageByUrl(this, music.musicCover, mMusicCover);
        mMusicName.setText(music.musicTitle);
        mSingerName.setText(music.musicArtist);
    }

    @Override
    public void onPlayerStart() {
        mBtnPlayPause.setImageResource(R.drawable.notify_btn_dark_pause_normal);
    }

    @Override
    public void onPlayerPause() {
        mBtnPlayPause.setImageResource(R.drawable.notify_btn_dark_play_normal);
    }

    @Override
    public void onPlayCompletion() {
        mBtnPlayPause.setImageResource(R.drawable.notify_btn_dark_play_normal);
    }

    @Override
    public void onError(String errorMsg) {
        mBtnPlayPause.setImageResource(R.drawable.notify_btn_dark_play_normal);
    }

    @Override
    public void onBuffering(boolean isFinishBuffer) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.get().removePlayerEventListener(this);
    }
}

