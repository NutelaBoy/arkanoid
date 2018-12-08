package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

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
        private boolean ballStopped = true;

        Canvas canvas;
        Paint paintWhite;
        Paint paintRed;
        private Paddle paddle;
        private Ball ball;


        //skore a zivoty
        private int lives = 3;
        private int score = 0;

        //Kostky
        private Brick[] bricks = new Brick[100];
        private int numberOfBricks = 0;
        private int brickWidth;
        private int brickHeight;

        public ArkanoidView(Context context){

            super(context);

            paintWhite = new Paint();
            paintRed = new Paint();
            surfHolder = getHolder();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            brickWidth = screenX / 8;
            brickHeight = screenY / 10;

            paddle = new Paddle(screenX,screenY);
            ball = new Ball(screenX,screenY);

            MediaPlayer mediaPlayer;




            createAndRestart();

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


            if(!ballStopped){
                paddle.update(fps);
                ball.update(fps);
            }


            if(RectF.intersects(paddle.getPaddle(),ball.getBall())) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearY(paddle.getPaddle().top - 2);
            }

            if(paddle.getPaddle().right > screenX){
                paddle.setMoving(paddle.STOP);
            }
            if(paddle.getPaddle().left < 0){
                paddle.setMoving(paddle.STOP);
            }

            for(int i = 0; i < numberOfBricks; i++){

                if (bricks[i].getVisibility()){

                    if(RectF.intersects(bricks[i].getBrick(),ball.getBall())) {
                        bricks[i].setInvisible();
                        ball.reverseYVelocity();
                        score = score + 10;

                    }
                }
            }


            if(ball.getBall().bottom > screenY){
                ball.reverseYVelocity();

                // Lose a life
                lives --;
                ball.reset(screenX,screenY);
                paddle.reset(screenX,screenY);
                ballStopped = true;

                if(lives > 0){
                    pause = true;
                    ballStopped = true;
                    createAndRestart();
                }

            }

            if(ball.getBall().top < 0){
                ball.reverseYVelocity();
                ball.clearY(12);
            }

            if(ball.getBall().left < 0){
                ball.reverseXVelocity();
                ball.clearX(2);
            }

            if(ball.getBall().right > screenX - 10){
                ball.reverseXVelocity();
                ball.clearX(screenX - 22);
            }
        }

        public void draw(){
            if(surfHolder.getSurface().isValid()){

                canvas = surfHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);

                paintWhite.setColor(Color.WHITE);
                canvas.drawRect(paddle.getPaddle(),paintWhite);
                canvas.drawRect(ball.getBall(),paintWhite);

                for(int i = 0; i < numberOfBricks; i++){
                    if(bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getBrick(), paintWhite);

                    }
                }

                paintRed.setColor(Color.RED);
                paintRed.setTextSize(40);
                canvas.drawText("Score: " + score + "   Lives: " + lives, 10,50, paintRed);



                surfHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void createAndRestart(){
            ball.reset(screenX,screenY);


            for(int column = 0; column < 8; column ++ ){
                for(int row = 0; row < 3; row ++ ){
                    bricks[numberOfBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numberOfBricks ++;
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    pause = false;
                    if(event.getX() > screenX/2){
                        ballStopped = false;
                        paddle.setMoving(paddle.RIGHT);
                    }
                    else{
                        ballStopped = false;
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
