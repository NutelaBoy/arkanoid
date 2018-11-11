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
    private int speed= 400;

    public final int STOP = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    public Paddle(int screenX, int screenY){
        //Velikost plosiny
        this.length = 200;
        this.height = 35;

        this.screenX = screenX;
        this.screenY = screenY;

        this.positionX = (screenX/2)-100;
        this.positionY = screenY - 35;

        this.paddle = new RectF(positionX,positionY, positionX+length, positionY+height);
    }

    public RectF getPaddle(){
        return this.paddle;
    }

    public void setMoving(int moving){
        this.moving = moving;
    }

    public void update(float fps){
        if(moving == LEFT){
            positionX = positionX - speed / fps;
        }
        if(moving == RIGHT){
            positionX = positionX + speed / fps;
        }


        paddle.left = positionX;
        paddle.right = positionX + length;
    }


}
