package com.example.multipleactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    EditText friendEditText;
    Button addFriendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        friendEditText = findViewById(R.id.friendEditText);
        addFriendButton = findViewById(R.id.addFriendButton);


    }

    public void addFriendButtonFunction(View view){
        String stringToPassBack = friendEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, stringToPassBack);
        intent.putExtra("maria", "Maria");
        setResult(RESULT_OK, intent);
        finish();
    }

}