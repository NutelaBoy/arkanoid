package com.example.matej.arkanoid;

import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF ball;
    private float xVelocity;
    private float yVelocity;

    private float positionX;
    private float positionY;

    private int ballHeight, ballWidth;


    public Ball(int screenX, int screenY){
        xVelocity = 200;
        yVelocity = -400;

        ballHeight = 10;
        ballWidth = 10;

        positionX =screenX/2;
        positionY = screenY - 60;

        ball = new RectF(positionX,positionY, ballHeight, ballWidth);
    }

    public RectF getBall(){
        return ball;
    }

    public void update(float fps){
        ball.left = ball.left + (xVelocity / fps);
        ball.top = ball.top + (yVelocity / fps);
        ball.right = ball.left + ballWidth;
        ball.bottom = ball.top - ballHeight;
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void clearY(float y){
        ball.bottom = y;
        ball.top = y - ballHeight;
    }

    public void clearX(float x){
        ball.left = x;
        ball.right = x + ballWidth;
    }

    public void reset(int x, int y){
        ball.left = x / 2;
        ball.top = y - 20;
        ball.right = x / 2 + ballWidth;
        ball.bottom = y - 20 - ballHeight;
    }


}
