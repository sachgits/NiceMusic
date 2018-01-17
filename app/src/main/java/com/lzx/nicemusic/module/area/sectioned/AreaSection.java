package com.lzx.nicemusic.module.area.sectioned;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzx.nicemusic.R;
import com.lzx.nicemusic.bean.MusicInfo;
import com.lzx.nicemusic.utils.FormatUtil;
import com.lzx.nicemusic.utils.GlideUtil;
import com.lzx.nicemusic.widget.SquareImageView;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by xian on 2018/1/15.
 */

public class AreaSection extends StatelessSection {

    private Context mContext;
    private List<MusicInfo> mMusicInfos = new ArrayList<>();

    public AreaSection(Context context, List<MusicInfo> musicInfos) {
        super(new SectionParameters.Builder(R.layout.section_area).build());
        mContext = context;
        mMusicInfos = musicInfos;
    }

    @Override
    public int getContentItemsTotal() {
        return mMusicInfos.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new AreaHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AreaHolder holder = (AreaHolder) viewHolder;
        MusicInfo musicInfo = mMusicInfos.get(position);
        GlideUtil.loadImageByUrl(mContext, musicInfo.getAlbumpicBig(), holder.mMusicCover);
        holder.mMusicName.setText(musicInfo.getSongname());
        holder.mMusicTitle.setText(musicInfo.getSingername());
        holder.mMusicPlayCount.setText(FormatUtil.formatNum(musicInfo.getSongid()));
    }

    class AreaHolder extends RecyclerView.ViewHolder {

        SquareImageView mMusicCover;
        TextView mMusicName, mMusicTitle, mMusicPlayCount;

        public AreaHolder(View itemView) {
            super(itemView);
            mMusicCover = itemView.findViewById(R.id.music_cover);
            mMusicName = itemView.findViewById(R.id.music_name);
            mMusicTitle = itemView.findViewById(R.id.music_title);
            mMusicPlayCount = itemView.findViewById(R.id.play_count);
        }
    }
}