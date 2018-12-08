package com.example.matej.arkanoid;

import android.graphics.RectF;

public class Brick {

    private RectF brick;
    private boolean isVisible;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 3;

        brick = new RectF(column * width + padding,
                          row * height + padding,
                         column * width + width -padding,
                        row * height + height - padding);
    }


    public RectF getBrick(){
        return this.brick;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
