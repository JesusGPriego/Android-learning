package com.example.notas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button newNoteButton;
    private RecyclerView recyclerView;
    //Puente entre la información y el recyclerview
    private NotaAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    SharedPreferences sharedPreferences;

    ArrayList<Nota> notaArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = this.getSharedPreferences("com.example.notas", Context.MODE_PRIVATE);

        readNotes();


        newNoteButton = findViewById(R.id.newNoteButton);
        newNoteButton.setOnClickListener(v -> newNoteActivity());

        setRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.newNote:
                newNoteActivity();
                return true;
            case R.id.about:
                aboutActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
         * As result of both activities (new or edit note), a request code is send back.
         * 2 means edit, 1 means new.
         */
        if (requestCode == 2){
            String titulo = data.getStringExtra("titulo");
            String texto = data.getStringExtra("texto");
            int position = data.getIntExtra("position", 0);
            if(!texto.equals(notaArrayList.get(position).getText())
                    || !titulo.equals(notaArrayList.get(position).getTitle()) ) {
                editNote(titulo, texto, position);
            }
        }else if(requestCode == 1){
            String titulo = data.getStringExtra("titulo");
            String texto = data.getStringExtra("texto");
            newNote(titulo, texto);
        }

    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NotaAdapter(notaArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> editActivity(position));

        adapter.setOnItemLongClickListener(position -> {
            createDialog(position);
            return true;
        });
    }

    private void newNoteActivity() {
        Intent intent=new Intent(MainActivity.this, NewNota.class);
        startActivityForResult(intent, 1);// Activity is started with requestCode 2
    }

    private void editActivity(int position) {
        Intent intent=new Intent(MainActivity.this, EditNota.class);
        intent.putExtra("titulo", notaArrayList.get(position).getTitle());
        intent.putExtra("texto", notaArrayList.get(position).getText());
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
    }

    private void aboutActivity(){
        Intent intent = new Intent(MainActivity.this, aboutActivity.class);
        startActivity(intent);
    }

    private void saveNotes() {
        try {
            sharedPreferences.edit().putString("notas", ObjectSerializer.serialize(notaArrayList)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readNotes() {
        try {
            notaArrayList = (ArrayList<Nota>) ObjectSerializer.deserialize(sharedPreferences.getString("notas",
                    ObjectSerializer.serialize(new ArrayList<Nota>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newNote(String titulo, String texto) {
        notaArrayList.add(new Nota(titulo, texto));
        adapter.notifyItemInserted(notaArrayList.size()-1);
        saveNotes();
    }

    private void editNote(String titulo, String texto, int position){
        notaArrayList.get(position).setTitle(titulo);
        notaArrayList.get(position).setText(texto);
        adapter.notifyItemChanged(position);
        saveNotes();
    }

    private void deleteNote(int position){
        notaArrayList.remove(position);
        adapter.notifyItemRemoved(position);
        saveNotes();
        Toast.makeText(this,"Nota borrada", Toast.LENGTH_SHORT).show();

    }

    public void createDialog(int position){
        new AlertDialog.Builder(this)
                .setTitle("¿Borrar nota?")
                .setMessage("Se borrará la nota seleccionada.")
                .setPositiveButton("Sí", (dialog, which) -> {
                    deleteNote(position);
                })
                .setNegativeButton("No", (dialog, which) -> {

                })
                .show();
    }
}