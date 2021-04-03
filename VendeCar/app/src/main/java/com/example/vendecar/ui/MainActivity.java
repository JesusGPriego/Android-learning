package com.example.vendecar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendecar.R;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButtonLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setImageButtonLaunch();

    }

    private void setImageButtonLaunch(){
        //Click on the image to start the actuall app.
        imageButtonLaunch = findViewById(R.id.imageButtonLaunch);
        imageButtonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                startActivity(intent);
            }
        });
    }


}