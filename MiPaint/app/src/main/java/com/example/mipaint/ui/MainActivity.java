package com.example.mipaint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipaint.R;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageViewAdd;
    private Button buttonMain;
    private Animation anim;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewLogo);
        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Mi Paint");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
        textView.setTextColor(Color.parseColor("#F97C3C"));
        imageViewAdd = findViewById(R.id.imageViewAdd);
        imageViewAdd.setOnTouchListener((v, event) -> {
            rotateImageViewAdd(event);
            //Trick here is to return false, so onclick event will also be handled.
            return false;
        });
        buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnTouchListener((v, event) -> {
            rotateImageViewAdd(event);
            //Same as on imageviewadd.ontouch
            return false;
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.bailongo);
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        buttonMain.setOnClickListener(v-> toPaintActivity());
    }

    private void rotateImageViewAdd(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                anim = new RotateAnimation(0f, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setDuration(500);

                // Start animating the image
                imageViewAdd.startAnimation(anim);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bailongo);
            mediaPlayer.start();
        }
    }

    private void toPaintActivity() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }
}