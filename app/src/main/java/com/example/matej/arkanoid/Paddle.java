package com.example.matej.arkanoid;

import android.graphics.RectF;

public class Paddle {

    private RectF paddle;

    private float positionX;
    private float positionY;

    private int screenX;
    private int screenY;

    private int length;
    private int height;

    private int moving;
    private int speed= 200;

    public Paddle(int screenX, int screenY){
        //Velikost plosiny
        this.length = 130;
        this.height = 25;

        this.screenX = screenX;
        this.screenY = screenY;

        this.positionX = screenX/2;
        this.positionY = screenY - 25;

        this.paddle = new RectF(positionX,positionY, positionX+length, positionY+height);

    }

    public RectF getPaddle(){
        return this.paddle;
    }

    public void setMoving(int moving){
        this.moving = moving;
    }

    public void update(long fps){
        if(moving > (screenX/2)){
            positionX = positionX - (speed / fps);
        }
        if(moving<= (screenX/2)){
            positionX = positionX + (speed / fps);
        }

        paddle.left = positionX;
        paddle.right = positionX + length;
    }


}
