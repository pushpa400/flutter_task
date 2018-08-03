package com.example.pushpar.flutur;



import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class Splash_Activity extends Activity {

    private static final String TAG = "Splash_Activity";
    MediaPlayer mplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_main);
        //mplayer=MediaPlayer.create(AnimationStarter.this,R.raw.music);
        start_playing();
        Button bounceBallButton = (Button) findViewById(R.id.bounceBallButton);
        final ImageView bounceBallImage = (ImageView) findViewById(R.id.bounceBallImage);

        bounceBallButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bounceBallImage.clearAnimation();
                TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0,
                        getDisplayHeight()/2);
                transAnim.setStartOffset(500);
                transAnim.setDuration(3000);
                transAnim.setFillAfter(true);
                transAnim.setInterpolator(new BounceInterpolator());
                transAnim.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.i(TAG, "Starting button dropdown animation");

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.i(TAG,
                                "Ending button dropdown animation. Clearing animation and setting layout");
                        bounceBallImage.clearAnimation();
                        final int left = bounceBallImage.getLeft();
                        final int top = bounceBallImage.getTop();
                        final int right = bounceBallImage.getRight();
                        final int bottom = bounceBallImage.getBottom();
                        bounceBallImage.layout(left, top, right, bottom);
                        Intent intent=new Intent(Splash_Activity.this,Gender_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                bounceBallImage.startAnimation(transAnim);

            }
        });

    }
    public void start_playing()
    {

        if(mplayer!=null && mplayer.isPlaying()){
            mplayer.stop();
            mplayer.reset();
            mplayer.release();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mplayer=MediaPlayer.create(Splash_Activity.this,R.raw.music);
                mplayer.start();
            }
        }).start();
    }
    public void stop_playing(){
        if(mplayer==null)
        {
            return;
        }
        if(mplayer.isPlaying()){
            mplayer.stop();
            mplayer.reset();
            mplayer.release();
        }

    }
    @Override
    protected void onPause(){
        super.onPause();
        stop_playing();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mplayer==null)
        {
            return;
        }

        start_playing();
    }
    /*@Override
    protected void onDestroy(){
        super.onDestroy();
        stop_playing();
    }*/

    private int getDisplayHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }
}
