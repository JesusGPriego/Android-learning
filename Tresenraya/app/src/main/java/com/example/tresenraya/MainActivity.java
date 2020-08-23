package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * activePlayer will determinate what player moves.
     * 0 = yellow, 1 = red.
     */
    private int activePlayer=0;
    /**
     * This array will be used to track what place is empty (2), yellow(0) or red(1)
     */
    private int[] tracks = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    /**
     * Winning positions based on the GridLayout position.
     */
    private int [] [] winningPlaces ={{0,1,2}, {3,4,5}, {6,7,8}, {0,4,8}, {2,4,6}, {0,3,6}, {1,4,7}, {2,5,8}};
    /**
     * This boolean will tell us if the game is running or not.
     */
    private boolean isGameActive = true;
    /**
     * This int will tell us how many times a coin is dropped.
     */
    private int counter;
    /**
     * MediaPlayer to suit the background track.
     */
    private MediaPlayer backgroundTrack;
    /**
     * MediaPlayer to suit the coin tick.
     */
    private MediaPlayer coinTick;

    /**
     * Default launcher app method. I use it to initialize some vars.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = 0;
        backgroundTrack= MediaPlayer.create(this, R.raw.bailongo);
        backgroundTrack.setLooping(true);
        backgroundTrack.start();
        coinTick = MediaPlayer.create(this, R.raw.coin);

    }

    /**
     * Will make the coin fall down and rotate.
     * @param image
     */
    public void animationSetter(ImageView image){
        image.setTranslationY(-1500);
        image.animate().translationYBy(1500).rotation(1800).setDuration(250);
    }

    /**
     * This method gets the tag of the place tapped and stores it in the tracks array.
     * @param image image tapped
     */
    public void tokenTracker(ImageView image){
        int imageTag = Integer.parseInt(image.getTag().toString());
        tracks[imageTag] = activePlayer;
    }

    /**
     * Will restart the game if the button playagain is tapped.
     * @param view
     */
    public void reset(View view){
        TextView txt1 = (TextView)findViewById(R.id.txt1);
        Button btn1 = (Button)findViewById(R.id.btn1);
        GridLayout grid = (GridLayout)findViewById(R.id.grid1);
        for (int i=0; i<grid.getChildCount(); i++){
            ImageView img1 = (ImageView)grid.getChildAt(i);
            img1.setImageDrawable(null);
            img1.setClickable(true);
            int imageTag = Integer.parseInt(img1.getTag().toString());
            tracks[imageTag] = 2;
        }
        txt1.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        counter=0;
        isGameActive=true;

    }

    /**
     * This method will be called every time the player taps a position succesfully.</br>
     * It increase the counter var by 1 on tap. If it reaches 9, game will be stopped and </br>
     * the try again button will be displayed.
     * @param tag ImageView's tapped tag.
     */
    public void isDraw(int tag){
        if(tracks[tag]!=2){
            counter++;
        }

        if(counter == 9){
            TextView txt1 = (TextView)findViewById(R.id.txt1);
            txt1.setVisibility(View.VISIBLE);
            txt1.setTextColor(Color.GREEN);
            txt1.setText("Draw!!");
            Button btn1 = (Button)findViewById(R.id.btn1);
            btn1.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Checks tracks array vs winnningPlaces array.</br>
     * If there aren't 2s in the tracks array, it will check who's the winner, in case that
     * 0s or 1s matches the winning places. Else, it would be a draw.
     */
    public void whoWins(){
        for (int[] winningPlace : winningPlaces){
            if(tracks[winningPlace[0]] == tracks[winningPlace[1]] && tracks[winningPlace[1]] == tracks[winningPlace[2]] && tracks[winningPlace[0]]!=2){
                String winner="";
                TextView txt1 = (TextView)findViewById(R.id.txt1);
                Button btn1 = (Button)findViewById(R.id.btn1);
                if(tracks[winningPlace[0]]==0){
                    winner = "Yellow";
                    txt1.setTextColor(Color.YELLOW);
                }else{
                    winner= "Red";
                    txt1.setTextColor(Color.RED);
                }
                txt1.setText(winner + " has won!!");
                txt1.setVisibility(View.VISIBLE);
                txt1.clearAnimation();
                btn1.setVisibility(View.VISIBLE);
                isGameActive=false;
            }
        }
    }

    /**
     * If the game is active, and the place selected is not filled yet, a coin will be dropped.
     * @param view
     */
    public void dropIn(View view){

        ImageView tapped = (ImageView)view;
        if(isGameActive) {
            tokenTracker(tapped);
            if (activePlayer == 0) {
                tapped.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                tapped.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            //if there's a coin tickin, it stops and release the resource, then it
            //remake the resource and plays it again, allowing us to multitap and hear the sound
            //every time
            if(coinTick.isPlaying()){
                coinTick.stop();
                coinTick.release();
                coinTick = (MediaPlayer.create(this, R.raw.coin));
            }
            coinTick.start();
            tapped.setClickable(false);
            animationSetter(tapped);
            whoWins();
            isDraw(Integer.parseInt((tapped.getTag().toString())));
        }else{
            Toast.makeText(this, "Game is finished.", Toast.LENGTH_SHORT).show();
        }
    }

}