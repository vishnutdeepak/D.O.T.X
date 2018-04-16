package com.example.vishnu.gridgame;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.media.MediaPlayer;



public class Splash extends AppCompatActivity {

    MediaPlayer varienop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        varienop = MediaPlayer.create(this, R.raw.themenew);
        varienop.setLooping(true);
        varienop.start();





        Typeface typeFace = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");


        final TextView playgame = (TextView) findViewById(R.id.playgame);

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        RelativeLayout forest = (RelativeLayout)findViewById(R.id.forest);
        forest.setSystemUiVisibility(uiOptions);


        playgame.setTypeface(typeFace);
       

        final Intent i = new Intent(this, MainActivity.class);
        playgame.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {



                varienop.stop();


                startActivity(i);






            }
        });

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        varienop.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();


        RelativeLayout forest = (RelativeLayout)findViewById(R.id.forest);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        forest.setSystemUiVisibility(uiOptions);
        varienop = MediaPlayer.create(this, R.raw.themenew);
        varienop.setLooping(true);
        varienop.start();




    }
    @Override
    protected void onPause(){
        super.onPause();
        varienop.stop();
    }



}
