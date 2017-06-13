package com.example.bhatt.baking;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by bhatt on 12-06-2017.
 */

public class Fragment2 extends Fragment {


    private String description;
    private String videolink;

//    @BindView(R.id.exo_player_description) TextView textView;

    private TextView textView;


    private SimpleExoPlayerView simpleExoPlayerView;

    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;

    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private DefaultExtractorsFactory extractorsFactory;

    Context context;

    View fragment2;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragment2 = inflater.inflate(R.layout.fragment2,container,false);

        context = getActivity().getApplicationContext();
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

        textView = (TextView)fragment2.findViewById(R.id.exo_player_description);


        return fragment2;

    }

    private void initializePlayer(String description,String videolink) {


        textView.setText(description);

        simpleExoPlayerView = (SimpleExoPlayerView)fragment2.findViewById(R.id.exo_player);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setVisibility(View.VISIBLE);


        if(videolink.isEmpty()){
            simpleExoPlayerView.setVisibility(View.GONE);
        }else {

            trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(
                    context,
                    trackSelector,
                    new DefaultLoadControl());
            simpleExoPlayerView.setPlayer(player);

            extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(videolink),
                    mediaDataSourceFactory,
                    extractorsFactory,
                    null,
                    null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(shouldAutoPlay);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(player != null){
            relesePlayer();
        }
    }

    private void relesePlayer(){
        player.stop();
        player.release();
        player = null;
    }

    public void update(String description,String videolink){
        this.description = description;
        this.description = videolink;

        if(player != null){
            relesePlayer();
            initializePlayer(description,videolink);

        }else {
            initializePlayer(description,videolink);
        }



    }
}
