package com.terry.gameloop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Created by liaojinliang on 16/4/3.
 */
public class FireBullet {
    protected float x,y;
    protected float radius;
    private Paint paint;
    private static int speed = -50;
    //constructor
    public FireBullet(float initX, float initY){
        x = initX;
        y = initY;
        radius = 10;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
    }
    //draw the bullet
    public void draw(Canvas canvas){

        canvas.drawCircle(x,y,radius,paint);
    }
    //update position
    public void update(){
        y += speed;
        if (y< 10){
            y = 0;
        }
    }
    //
    public boolean disappear(){
        if(y < 10){
            return true;
        } else {
            return false;
        }
    }
    //get bullet position
    public float getX(){return x;}
    public float getY(){return y;}


}
