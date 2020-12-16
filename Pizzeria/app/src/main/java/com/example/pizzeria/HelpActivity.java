package com.example.pizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private TextView helpTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setHelpTextViewText();
    }

    private void setHelpTextViewText(){
        helpTextView = findViewById(R.id.helpTextView);
        helpTextView.setText("Pantalla principal:\nPulsar en Hacer pedido para iniciar el Pedido." +
                "\nEn la pantalla Pizzas, para añadir una Pizza al pedido, pulsar sobre el + en su respectiva tarjeta." +
                "\nEn la pantalla Bebidas, para añadir una Bebida al pedido, pulsar sobre el + en su respectiva tarjeta." +
                "\nLa pantalla final nos muestra un desglose del pedido. Pulsar finalizar para acabar." +
                "\n\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris vehicula ut mi tincidunt congue. " +
                "Mauris nunc urna, pellentesque at nulla ac, varius suscipit metus. Nullam vitae neque libero. Vivamus faucibus " +
                "a libero non tristique. Curabitur eget placerat velit, et vestibulum nibh. Fusce sit amet dignissim ligula, a " +
                "ultricies nunc. Nulla a quam bibendum, tristique magna eget, maximus nulla. " +
                "Nulla facilisi. Curabitur tortor arcu, fermentum imperdiet neque at, pellentesque pulvinar justo. Mauris " +
                "laoreet laoreet bibendum. Proin luctus tortor sed sem malesuada, et aliquam metus vehicula. Maecenas facilisis risus " +
                "erat, eget sodales erat eleifend nec. Sed ac tortor ac leo finibus lacinia sit amet eget nunc. Class aptent taciti sociosqu" +
                " ad litora torquent per conubia nostra, per inceptos himenaeos. Orci varius natoque penatibus et magnis dis parturient " +
                "montes, nascetur ridiculus mus. Vestibulum non nulla pulvinar, posuere justo euismod, dapibus mi. Maecenas blandit " +
                "egestas erat ac facilisis. Aliquam accumsan varius sapien, id blandit ligula tempus a. Phasellus eget mattis neque. " +
                "Nunc ut nulla mattis, imperdiet sem ac, posuere neque. Nam iaculis purus in ex dignissim, eget bibendum nisi sodales. ");
    }

    public void volver(View v){
        finish();
    }

}