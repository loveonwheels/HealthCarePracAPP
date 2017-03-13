package com.mSIHAT.hcp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.util.Constants;

public class MainActivity extends AppCompatActivity {
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreferences.Editor editor2 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
        editor2.putBoolean("opened", false);
        editor2.commit();
        this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.move, R.anim.moveout).replace(R.id.loaderframe, new LoadingFragment(), "loadingfragment").commit();
      //  progress=(ProgressBar)findViewById(R.id.progressBar);
       // progress.setProgress(10);
// Get the Drawable custom_progressbar
        //  Drawable draw = getDrawable(R.drawable.custom_progressbar);
// set the drawable as progress drawable
        // progress.setProgressDrawable(draw);
       // startProgress(getWindow().getDecorView().findViewById(android.R.id.content));
    }
/*
    public void startProgress(View view) {
        progress.setProgress(0);
        new Thread(new Task()).start();
    }

    class Task implements Runnable {


        @Override
        public void run() {

            for(
                    int i = 0; i<=100;i++)

            {
                final int value = i;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress.setProgress(value);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.push_down_out);


                }


            });

            //  Thread.currentThread().destroy();
        }
    }*/
}
