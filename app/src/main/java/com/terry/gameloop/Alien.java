package com.terry.gameloop;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by liaojinliang on 16/4/3.
 */
public class Alien extends FireBullet {

    protected int speed;
    private Bitmap bm;
    private int width;
    public Alien(Bitmap res, float initX, float initY,int _width, int _speed) {
        super(initX, initY);
        bm = res;
        radius = bm.getWidth()/2;
        width = _width;
        speed = _speed;
    }

//update aliens position
    public void update(){
        x += speed;
    }
//updating moving side
    public void SideUpdate(int _speed){
        speed = _speed;
        y += 30;
    }

//detect if the bullet hit on alien
    public boolean hitDetect(float bltX, float bltY){
        if(x > bltX - radius*1.2 && x < bltX + radius*1.2 && y > bltY -radius*1.2 && y < bltY + radius*1.2){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bm,x,y,null);
    }


//detect if alien hit the bound
    public boolean bounceCheck(){
        if (x < 0 || x > width-radius*2){
          return true;
        }

        return false;
    }



}
