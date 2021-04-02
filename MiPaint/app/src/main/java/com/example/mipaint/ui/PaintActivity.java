package com.example.mipaint.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.mipaint.R;
import com.example.mipaint.control.PaintSurfaceView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PaintActivity extends AppCompatActivity {

    private PaintSurfaceView paintSurfaceView;
    private FloatingActionButton FABbrushes;
    private LinearLayout linearLayoutMenu;
    private boolean menuVisibility;
    private ImageButton imageButtonRedCircle;
    private ImageButton imageButtonBlueCircle;
    private ImageButton imageButtonYellowCircle;
    private ImageButton imageButtonGreenCircle;
    private ImageButton imageButtonStar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        //Var inits and button onclick event handling
        paintSurfaceView = findViewById(R.id.surfaceView);
        FABbrushes = findViewById(R.id.FABbrushes);
        linearLayoutMenu = findViewById(R.id.linearLayoutMenu);
        FABbrushes.setOnClickListener(v -> setMenuVisibility());
        imageButtonBlueCircle = findViewById(R.id.imageButtonBlueCicle);
        imageButtonBlueCircle.setOnClickListener(v -> {
            changePaint("#FF3700B3",
                    PaintSurfaceView.SHAPE_CIRCLE);
            setSelectedButtonStyle(imageButtonBlueCircle);

        });
        imageButtonGreenCircle = findViewById(R.id.imageButtonGreenCircle);
        imageButtonGreenCircle.setOnClickListener(v ->{
            changePaint("#00FF0D",
                    PaintSurfaceView.SHAPE_CIRCLE);
            setSelectedButtonStyle(imageButtonGreenCircle);
        });
        imageButtonRedCircle = findViewById(R.id.imageButtonRedCircle);
        imageButtonRedCircle.setOnClickListener(v -> {
            changePaint("#FF0000",
                    PaintSurfaceView.SHAPE_CIRCLE);
            setSelectedButtonStyle(imageButtonRedCircle);
        });
        imageButtonYellowCircle = findViewById(R.id.imageButtonYellowCircle);
        imageButtonYellowCircle.setOnClickListener(v -> {
            changePaint("#F8FF2E",
                    PaintSurfaceView.SHAPE_CIRCLE);
            setSelectedButtonStyle(imageButtonYellowCircle);
        });
        imageButtonStar = findViewById(R.id.imageButtonStar);
        imageButtonStar.setOnClickListener(v -> {
            changePaint(null,
                    PaintSurfaceView.SHAPE_STAR);
            setSelectedButtonStyle(imageButtonStar);
        });
        setDefaultBrush();
        menuVisibility = false;
    }

    /**
     * Toggles the menu. If it is showed, it dissappear with animation. Else, it appears with animation.
     */
    private void setMenuVisibility(){
        if (!menuVisibility) {
            animationIn();
        }else if(menuVisibility){
            animationOut();
        }
    }

    /**
     * Changes the menu visibility to visible, and applies an animation to it.
     */
    private void animationIn(){
        Animation animation = new TranslateAnimation(500, 0, 0, 0);
        animation.setDuration(400);
        animation.setFillAfter(true);
        linearLayoutMenu.startAnimation(animation);
        linearLayoutMenu.setVisibility(View.VISIBLE);
        menuVisibility = true;
    }

    /**
     * Changes the menu visibility to gone, and applies an animation to it.
     */
    private void animationOut(){
        Animation animation = new TranslateAnimation(0, 500, 0, 0);
        animation.setDuration(400);
        animation.setFillAfter(true);
        linearLayoutMenu.startAnimation(animation);
        linearLayoutMenu.setVisibility(View.GONE);
        menuVisibility = false;
    }

    /**
     * applies a different background to the menu button that is clicked by the user. </br>
     * This way, the user can always be sure what brush is using to paint.
     * @param v its id is checked to apply the style to a concrete brush.
     */
    private void setSelectedButtonStyle(View v){
        setDefaultButtonStyle();
        switch(v.getId()){
            case R.id.imageButtonStar:
                v.setBackgroundResource(R.drawable.selected_button_background);
                break;
            case R.id.imageButtonBlueCicle:
                v.setBackgroundResource(R.drawable.selected_button_background);
                break;
            case R.id.imageButtonRedCircle:
                v.setBackgroundResource(R.drawable.selected_button_background);
                break;
            case R.id.imageButtonGreenCircle:
                v.setBackgroundResource(R.drawable.selected_button_background);
                break;
            case R.id.imageButtonYellowCircle:
                v.setBackgroundResource(R.drawable.selected_button_background);
                break;
            default:
                //
        }
        animationOut();
    }

    /**
     * Applies the default style to every button.
     */
    private void setDefaultButtonStyle(){
        imageButtonRedCircle.setBackgroundResource(R.drawable.default_button_background);
        imageButtonStar.setBackgroundResource(R.drawable.default_button_background);
        imageButtonBlueCircle.setBackgroundResource(R.drawable.default_button_background);
        imageButtonGreenCircle.setBackgroundResource(R.drawable.default_button_background);
        imageButtonYellowCircle.setBackgroundResource(R.drawable.default_button_background);
    }

    /**
     * Calls PaintSurfaceView changePaint method, to apply a different brush.
     * @param color the color for the brush (Paint object in PaintSurfaceView)
     * @param shape the shape that will be painted in the canvas.
     */
    private void changePaint(String color, String shape){
        paintSurfaceView.setPainting(color, shape);
    }

    private void setDefaultBrush(){
        changePaint("#FF0000",
                PaintSurfaceView.SHAPE_CIRCLE);
        setSelectedButtonStyle(imageButtonRedCircle);
    }

    /**
     * Setting custom menu in this activity
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.paint_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemClear:
                paintSurfaceView.clearCanvas();
                return true;
            case R.id.menuItemBack:
                finish();
                return true;
            case R.id.menuItemUndo:
                paintSurfaceView.undo();
                //TODO: redo
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}