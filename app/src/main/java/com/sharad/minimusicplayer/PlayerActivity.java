package com.sharad.minimusicplayer;

import static com.sharad.minimusicplayer.MainActivity.audioList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView next,prev,play;
    static ArrayList<Audio> audioList3;
    int cp = 0;
    SeekBar seekBar;
    TextView songName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        next = findViewById(R.id.imageView4);
        play = findViewById(R.id.imageView3);
        prev = findViewById(R.id.imageView2);
        seekBar = findViewById(R.id.seekBar);
        songName = findViewById(R.id.textView);

        songName.setSelected(true);
        audioList3 = audioList;
        Intent m = getIntent();
        int t = m.getIntExtra("SongP",0);
        cp = t;


        long iddd = Long.parseLong(audioList3.get(cp).getData());
        Uri myuri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,iddd);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myuri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        songName.setText(audioList3.get(cp).getTitle());
        if(mediaPlayer.isPlaying()){
            play.setImageResource(R.drawable.ic_baseline_pause_24);
        }
        seekBar.setMax(mediaPlayer.getDuration());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int current_pos = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress((int)current_pos);
                    handler.postDelayed(this, 1000);
                } catch (Exception ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }


                cp = cp-1;
                long iddd = Long.parseLong(audioList3.get(cp).getData());
                Uri myuri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,iddd);

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), myuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                songName.setText(audioList3.get(cp).getTitle());
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }


                cp = cp+1;
                long iddd = Long.parseLong(audioList3.get(cp).getData());
                Uri myuri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,iddd);

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), myuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                songName.setText(audioList3.get(cp).getTitle());
                if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }else{
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        mediaPlayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}