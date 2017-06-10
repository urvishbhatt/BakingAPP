package com.example.bhatt.baking;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.BindView;

public class MainActivity3 extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

        setContentView(R.layout.activity_main3);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView)findViewById(R.id.exo_player_description);
        description = getIntent().getStringExtra("description");
        textView.setText(description);

        videolink = getIntent().getStringExtra("videourl");

        initializePlayer(videolink);
    }


    private void initializePlayer(String videolink) {

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player);
        simpleExoPlayerView.requestFocus();

        if(videolink.isEmpty()){
            simpleExoPlayerView.setVisibility(View.GONE);
        }else {

            trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(
                    this,
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
    protected void onDestroy() {
        super.onDestroy();
        if(player != null){
            relesePlayer();
        }
    }
    private void relesePlayer(){
        player.stop();
        player.release();
        player = null;
    }

}
