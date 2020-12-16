package com.example.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNota extends AppCompatActivity {

    private EditText tiituloEditText;
    private EditText textoEditText;
    private Button saveButton;
    private int position;
    private String titulo;
    private String texto;
    private Intent receivedIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nota);

        tiituloEditText = findViewById(R.id.TituloEditText);
        textoEditText = findViewById(R.id.textoEditText);
        saveButton = findViewById(R.id.saveButton);

        receivedIntent = getIntent();
        position = receivedIntent.getIntExtra("position", 0);
        titulo = receivedIntent.getStringExtra("titulo");
        texto = receivedIntent.getStringExtra("texto");

        tiituloEditText.setText(titulo);
        textoEditText.setText(texto);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendIntentBack();
            }
        });
    }

    /**
     * If the user press back button, changes will be saved.
     *
     */
    @Override
    public void onBackPressed() {

        sendIntentBack();

        super.onBackPressed();
    }

    /**
     * Creates and adds info to the intent that is send back to main activity.
     * </br> It sends the text written in both edit texts and the position of the note </br>
     * edited.
     */
    private void sendIntentBack() {
        String titulo=tiituloEditText.getText().toString();
        String texto = textoEditText.getText().toString();
        int posicion = position;
        Intent intent=new Intent();
        intent.putExtra("titulo",titulo);
        intent.putExtra("texto", texto);
        intent.putExtra("position", posicion);
        setResult(2,intent);
        finish();//finishing activity
    }
}