package com.example.mipaint.control;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mipaint.entity.Painting;

import java.util.Vector;

import static com.example.mipaint.control.PaintSurfaceView.SHAPE_CIRCLE;
import static com.example.mipaint.control.PaintSurfaceView.SHAPE_STAR;
import static com.example.mipaint.control.PaintSurfaceView.bitmapStar;

public class PaintingThread extends Thread{
    /**
     * holder for which this thread will work
     */
    private SurfaceHolder surfaceHolder;
    /**
     * SurfaceView this Thread will paint
     */
    private PaintSurfaceView paintSurfaceView;
    /**
     * running flag. We take control on the thread with this.
     */
    private boolean run;
    private Canvas canvas;
    private Paint paint;

    public PaintingThread(SurfaceHolder surfaceHolder, PaintSurfaceView paintSurfaceView){
        this.paintSurfaceView = paintSurfaceView;
        this.surfaceHolder = surfaceHolder;
        run = false;
        this.canvas = null;
        this.paint = new Paint();
    }



    /**
     * It set run var value. If true, the Thread will work. Else, it won't.
     * @param run
     * @return
     */
    public void setRunning(boolean run){
        this.run = run;
    }

    public void clearCanvas(){
        this.canvas.drawColor(Color.WHITE);
    }

    public void unDo(Vector<Painting> vector){
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < vector.size(); i++) {
            if (vector.elementAt(i).getShape().equals(SHAPE_CIRCLE)) {
                paint.setColor(Color.parseColor(vector.elementAt(i).getColor()));
                canvas.drawCircle(vector.elementAt(i).getPosX(),
                        vector.elementAt(i).getPosY(), 20, paint);
            } else if (vector.elementAt(i).getShape().equals(SHAPE_STAR)) {
                canvas.drawBitmap(bitmapStar, vector.elementAt(i).getPosX(),
                        vector.elementAt(i).getPosY(), null);
            }
        }
    }

    @Override
    public void run() {

        while(run) {
            try {
                canvas = paintSurfaceView.getHolder().lockCanvas(null);
                synchronized(surfaceHolder)
                {
                    // En cada iteracción repintamos todo el lienzo con la información que hay en el vector
                    paintSurfaceView.paint(canvas);
                }
            }
            finally {
                if(canvas != null) {
                    paintSurfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
