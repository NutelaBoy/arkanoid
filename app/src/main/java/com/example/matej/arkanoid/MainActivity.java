package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
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
        private float fps = 1;
        private float fpsTime;

        private int screenX;
        private int screenY;

        private boolean pause;
        volatile boolean playing;

        Canvas canvas;
        Paint paintWhite;
        private Paddle paddle;
        private Ball ball;

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
            ball = new Ball(screenX,screenY);
            draw();
        }

        @Override
        public void run() {

            while (playing) {
                long startFrame = System.currentTimeMillis();

                if(!pause){
                   update();
                }

                draw();

                fpsTime = System.currentTimeMillis() - startFrame;
                if (fpsTime >= 1) {
                    fps = 1000 / fpsTime;
                }

            }

        }


        public void update(){
            paddle.update(fps);
            ball.update(fps);
        }

        public void draw(){
            if(surfHolder.getSurface().isValid()){

                canvas = surfHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);

                paintWhite.setColor(Color.WHITE);
                canvas.drawRect(paddle.getPaddle(),paintWhite);
                canvas.drawRect(ball.getBall(),paintWhite);

                surfHolder.unlockCanvasAndPost(canvas);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    pause = false;
                    if(event.getX() > screenX/2){
                        paddle.setMoving(paddle.RIGHT);
                    }
                    else{
                        paddle.setMoving(paddle.LEFT);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    paddle.setMoving(paddle.STOP);
                    break;
            }

            return true;
        }

        public void pause(){
            playing = false;
            try{
                gameThread.join();
            }catch (InterruptedException e){
                Log.d("Error:","Thread error");
            }

        }

        public void resume(){
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        arkanoidView.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        arkanoidView.pause();
    }

}
