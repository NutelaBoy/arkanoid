package com.example.matej.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ArkanoidView extends SurfaceView implements Runnable {

    //vlakno hry
    private Thread gameThread = null;
    //holder pro canvas
    private SurfaceHolder surfHolder;

    Canvas canvas;
    Paint paint;

    public ArkanoidView(Context context){
        super(context);

        paint = new Paint();
        surfHolder = getHolder();
    }

    @Override
    public void run() {

    }

    public void draw(){
        if(surfHolder.getSurface().isValid()){

            canvas = surfHolder.lockCanvas();
            canvas.drawColor(Color.LTGRAY);
        }
    }

}
