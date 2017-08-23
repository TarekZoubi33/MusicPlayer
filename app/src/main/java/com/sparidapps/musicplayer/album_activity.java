package com.sparidapps.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class album_activity extends AppCompatActivity {

    String album_art;
    ImageView collapsing_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);
        Intent intent = getIntent();
        collapsing_image = (ImageView) findViewById(R.id.collapsing_image);
        album_art = intent.getStringExtra("album_art");
        if(album_art != null){
            Glide.with(this).load(album_art).into(collapsing_image);
        }
        else{
            Glide.with(this).load(R.drawable.album_art).into(collapsing_image);
        }


    }

}
