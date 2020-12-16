package com.example.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNota extends AppCompatActivity {

    private EditText tituloEditText2;
    private EditText textEditText2;
    private Button saveButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nota);

        tituloEditText2 = findViewById(R.id.TituloEditText2);
        textEditText2 = findViewById(R.id.textoEditText2);
        saveButton2 = findViewById(R.id.saveButton2);

        saveButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendIntentBack();
            }
        });

    }
    @Override
    public void onBackPressed() {
        sendIntentBack();
        super.onBackPressed();
    }

    private void sendIntentBack() {
        String titulo=tituloEditText2.getText().toString();
        String texto = textEditText2.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("titulo",titulo);
        intent.putExtra("texto", texto);
        setResult(1,intent);
        finish();//finishing activity
    }
}