package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SeekBar numberChoiceSB;
    private ArrayList<Integer> intArray;
    private ListView numberList;
    private int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBarTool();
        allFunction();
    }

    public void seekBarTool(){

        numberChoiceSB = (SeekBar)findViewById(R.id.numberChoiceSB);
        numberChoiceSB.setMax(20);
        numberChoiceSB.setProgress(1);
        value = numberChoiceSB.getProgress();
        final TextView txt1 = (TextView)findViewById(R.id.mText);
        txt1.setVisibility(View.INVISIBLE);
        numberChoiceSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value=i;
                txt1.setText(String.valueOf(value));
                allFunction();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                txt1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txt1.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void allFunction(){

        numberList = (ListView)findViewById(R.id.friendList);

        intArray = new ArrayList<Integer>();


        for(int i=0;i<10; i++){
            intArray.add(value*(i+1));
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, intArray);
        numberList.setAdapter(arrayAdapter);


    }





}