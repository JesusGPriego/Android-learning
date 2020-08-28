package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int secondsLeft;
    private int defaultTimer = 30;
    private int maxTime = 600;
    private int minTime = 1;
    private MediaPlayer tictac;
    private MediaPlayer alarm;
    private boolean timerOn = false;
    private CountDownTimer countDownTimer;
    private Button btnGo;
    private SeekBar timeBar;
    private boolean tictacFlag = false;
    private Button muteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeCountdownText(defaultTimer);
        tictac = MediaPlayer.create(this, R.raw.tictac);
        alarm = MediaPlayer.create(this, R.raw.alarm);
        timeBar = (SeekBar)findViewById(R.id.timeBar);
        timeBar.setProgress(defaultTimer);
        secondsLeft=defaultTimer;
        mkSeekBar();
        btnGo = (Button) findViewById(R.id.goBtn);
        muteBtn = (Button)findViewById(R.id.muteBtn);
    }


    public void myTimer(boolean start) {
        timerOn = true;
        if (start) {
            tictac.start();
            tictac.setLooping(true);
            tictacFlag = true;
            countDownTimer = new CountDownTimer(secondsLeft*1000, 500) {

                @Override
                public void onTick(long l) {
                    makeCountdownText((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    timerOn = false;
                    tictacOff();
                    alarm.start();
                    alarm.setLooping(true);
                    muteBtn.setBackgroundResource(R.drawable.soundoff);
                    btnGo.setText("Stop alarm!");
                    tictacFlag = false;
                }

            };

            countDownTimer.start();
        } else {
            countdownTimerCancel();
        }
    }


    public void makeCountdownText(int second) {

        int minutes = second / 60;
        int seconds = second - (minutes * 60);

        String minLeft = String.valueOf(minutes);
        String secLeft = String.valueOf(seconds);

        if(minLeft.length()==1){
            minLeft = "0" + minLeft;
        }

        if(secLeft.length()==1){
            secLeft = "0" + secLeft;
        }

        TextView timeLeftTxt = (TextView) findViewById(R.id.timeLeftTxt);
        timeLeftTxt.setText(minLeft + ":" + secLeft);

    }

    public void starTimer(View view) {

        if(!timerOn && alarm.isPlaying()){
            alarmOff();
            btnGo.setText("Start");
        }

        else if (!timerOn) {
            myTimer(true);
            timerOn = true;
            if(secondsLeft<1){
                secondsLeft=2;
            }
            btnGo.setText("Stop");
        } else {
            secondsLeft=defaultTimer;
            timeBar.setProgress(secondsLeft);
            makeCountdownText(secondsLeft);
            muteBtn.setBackgroundResource(R.drawable.soundoff);
            myTimer(false);
            timerOn = false;
            btnGo.setText("Start");
        }

    }

    public void tictacOff(){
        tictac.stop();
        tictac.release();
        tictac=MediaPlayer.create(this, R.raw.tictac);
    }

    public void alarmOff (){
        alarm.stop();
        alarm.release();
        alarm = MediaPlayer.create(this, R.raw.alarm);
    }
    public void countdownTimerCancel(){
        countDownTimer.cancel();
        if (tictac.isPlaying()) {
            tictacOff();
        }
        if (alarm.isPlaying()) {
            alarmOff();
        }
        timerOn=false;
        tictacFlag=false;
    }

    public void mkSeekBar(){

        timeBar.setMax(maxTime);

        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i<1){
                    i=1;
                }
                timeBar.setProgress(i);
                secondsLeft = i;
                makeCountdownText(secondsLeft);
                if(timerOn) {
                    countdownTimerCancel();
                }
                btnGo.setText("Start");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void muteTictac(View view){

        if(tictacFlag){
            if(tictac.isPlaying()) {
                tictacOff();
                Toast.makeText(getApplicationContext(), "tictac muted" , Toast.LENGTH_SHORT).show();
                muteBtn.setBackgroundResource(R.drawable.soundon);
            }else{
                tictac.start();
                Toast.makeText(getApplicationContext(), "tictac unmuted" , Toast.LENGTH_SHORT).show();
                muteBtn.setBackgroundResource(R.drawable.soundoff);
            }
        }

    }
}