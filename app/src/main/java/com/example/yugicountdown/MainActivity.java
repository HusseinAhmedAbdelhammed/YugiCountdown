package com.example.yugicountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerText;
    Button timerButton;
    SeekBar timerSB;
    MediaPlayer med;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        med=MediaPlayer.create(getApplicationContext(),R.raw.time);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText=(TextView)findViewById(R.id.timerText);
        timerButton=(Button)findViewById(R.id.timerButton);
        timerSB=(SeekBar) findViewById(R.id.timerSB);
        timerSB.setMax(600);
        timerSB.setProgress(30);
        timerSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                update(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control();

            }
        });
    }
    public void control(){
        if(counterIsActive==false){
        timerButton.setText("Stop");
        counterIsActive=true;
        timerSB.setEnabled(false);
        countDownTimer=new CountDownTimer(timerSB.getProgress() * 1000+100, 1000) {
            @Override
            public void onTick(long l) {
                update((int)l/1000);
            }

            @Override
            public void onFinish() {
                timerText.setText("0:00");
                med.start();
                med.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        reset();
                    }
                });


            }
        }.start();}else {
            reset();
        }

    }
    public void reset(){
        timerText.setText("0:30");
        timerSB.setProgress(30);
        countDownTimer.cancel();
        timerButton.setText("GO!");
        timerSB.setEnabled(true);
        counterIsActive=false;
    }
    public void update(int i){
        int minutes=(int)i/60;
        int seconds= i-minutes*60;
        String secondString=Integer.toString(seconds);
        if(seconds<10){
            secondString="0"+Integer.toString(seconds);
        }
        timerText.setText(Integer.toString(minutes)+":"+secondString);
    }
}