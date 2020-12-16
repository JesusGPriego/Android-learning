package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView aboutTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setAboutText();
    }

    private void setAboutText(){
        aboutTextView = findViewById(R.id.textViewAbout);
        aboutTextView.setText("Pizza House \n\n\nJesús García Priego\n\n\n©2021");
    }

    public void volver(View v){
        finish();
    }


}