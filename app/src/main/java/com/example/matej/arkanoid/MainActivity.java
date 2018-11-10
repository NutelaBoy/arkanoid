package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private ArkanoidView arkanoidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arkanoidView = new ArkanoidView(this);
        setContentView(arkanoidView);
    }

    public class ArkanoidView extends SurfaceView implements Runnable {

        //vlakno hry
        private Thread gameThread = null;
        //holder pro canvas
        private SurfaceHolder surfHolder;
        private long fps;
        private long fpsTime;

        private int screenX;
        private int screenY;


        Canvas canvas;
        Paint paintWhite;
        private Paddle paddle;

        public ArkanoidView(Context context){

            super(context);

            paintWhite = new Paint();
            surfHolder = getHolder();


            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;


            paddle = new Paddle(screenX,screenY);


        }

        @Override
        public void run() {

            long startFrame = System.currentTimeMillis();


            draw();





            fpsTime = System.currentTimeMillis() - startFrame;
            if (fpsTime >= 1) {
                fps = 1000 / fpsTime;
            }

        }


        public void update(){
            paddle.update(fps);
        }

        public void draw(){
            if(surfHolder.getSurface().isValid()){

                canvas = surfHolder.lockCanvas();
                canvas.drawColor(Color.LTGRAY);

                paintWhite.setColor(Color.WHITE);
                canvas.drawRect(paddle.getPaddle(),paintWhite);

                surfHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
