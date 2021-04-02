package com.example.mipaint.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mipaint.R;
import com.example.mipaint.entity.Painting;

import java.util.ArrayList;
import java.util.Vector;

public class PaintSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private PaintingThread paintingThread;
    private Vector<Painting> vectorPainting;
    private Paint paint;
    private String shape;
    private String color;
    public static final String SHAPE_CIRCLE = "circle";
    public static final String SHAPE_STAR = "star";
    public static Bitmap bitmapStar;
    private int flag;
    private ArrayList<Integer> flagArrayList;

    public PaintSurfaceView(Context context) {
        super(context);
        init();
    }

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    /**
     * Here is where the Thread is created and started.
     * @param holder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        paintingThread = new PaintingThread(getHolder(), this);
        paintingThread.setRunning(true);
        paintingThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * If the surface view is destroyed, the thread stops running.
     * @param holder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        stopThread();
    }

    /**
     * This method initializes all the vars, but the thread.
     */
    private void init(){
        //Getting callback
        getHolder().addCallback(this);

        //Vars
        flag = 0;
        vectorPainting = new Vector<>();
        flagArrayList = new ArrayList<>();
        paint = new Paint();
        paint.setAntiAlias(true);
        bitmapStar = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.star);
        //Method calls
        saveTrace();
    }

    /**
     * Saves the movement over the screen in a vector..
     */

    private void saveTrace(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int posXi = 0;
                int posYi = 0;
                /*
                 *As long as there is movement in the screen,newPaintings are addedto the vector
                 */
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    // Saving coords in local vars.
                    posXi = (int)event.getX();
                    posYi = (int)event.getY();
                    // Creating and storing a new Painting in the vector..
                    vectorPainting.addElement(new Painting(posXi, posYi, shape, color));

                }/*
                 * As soon as the finge lifts, and new paintings are added to the vector,
                 * programm checks what's the last value of flag in the array flagArrayList
                 * if it is the same, it is not added. No new painting, no new log.
                 */
                if(event.getAction() == MotionEvent.ACTION_UP){
                    flag = vectorPainting.size();
                    if(flag != 0) {
                        if(flagArrayList.size()>0) {
                            /*
                             * If values are different, means the vector stores now more paintings.
                             * And this means, there was a movement before the finger lift.
                             */
                            if (flagArrayList.get(flagArrayList.size() - 1) != flag){
                                flagArrayList.add(flag);
                            }else{
                                //No more painting, no new flag.
                            }
                        }else {
                            flagArrayList.add(flag);
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * Stops app subThread.
     */
    public void stopThread(){
        boolean retry = true;
        paintingThread.setRunning(false);

        while(retry) {
            try {
                paintingThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
    /**
     * Defines the style of the painting (shape and color).
     * @param color the color for the brush.
     * @param shape shape of the brush.
     */
    public void setPainting(String color, String shape){
        this.shape = shape;
        if(color != null) {
            this.color=color;
            paint.setColor(Color.parseColor(color));
        }
    }

    /**
     * This method return the canvas to fully white.
     */
    public void clearCanvas(){
        paintingThread.clearCanvas();
        flagArrayList.clear();
        vectorPainting.clear();
        flag = vectorPainting.size();
    }

    /**
     * Undoes the last stroke, and the last before the last, and so on till there is nothing to undo.
     */

    public void undo(){
        //If there is only one flag, means there is only one stroke, so the program clear the whole canvas.
        if(flagArrayList.size()==1){
            clearCanvas();

            /*
             * If there is more, the program looks for the last value in flagArrayList and the penultimate value.
             * the subtraction tell how many Paintings are between the last stroke's final painting, and
             * the last stroke's first painting. Then, it deletes all those painting from the vector, and
             * deletes the last flag in the flagArrayList
             */
        }else if(flagArrayList.size()> 1){
            int loopSize = flagArrayList.get(flagArrayList.size()-1) - flagArrayList.get(flagArrayList.size()-2);
            for(int i=1; i<=loopSize; i++){
                vectorPainting.remove(vectorPainting.lastElement());
            }
            paintingThread.unDo(vectorPainting);
            flagArrayList.remove(flagArrayList.get(flagArrayList.size()-1));
        }else{
            Toast.makeText(this.getContext(), "No possible undo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method with which the Thread will paint in the surface.
     * @param canvas
     */
    public void paint(Canvas canvas){
        // Canvas can be null, if the thread is not running.
        if(canvas!=null) {
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < vectorPainting.size(); i++) {
                if(vectorPainting.elementAt(i).getShape().equals(SHAPE_CIRCLE)) {
                    paint.setColor(Color.parseColor(vectorPainting.elementAt(i).getColor()));
                    canvas.drawCircle(vectorPainting.elementAt(i).getPosX(),
                            vectorPainting.elementAt(i).getPosY(), 20, paint);
                }else if(vectorPainting.elementAt(i).getShape().equals(SHAPE_STAR)){
                    canvas.drawBitmap(bitmapStar, vectorPainting.elementAt(i).getPosX(),
                            vectorPainting.elementAt(i).getPosY(), null);
                }
            }
        }
    }

}
