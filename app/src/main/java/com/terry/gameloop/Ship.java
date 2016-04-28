package com.terry.gameloop;

import android.graphics.Bitmap;

/**
 * Created by liaojinliang on 16/4/3.
 */
public class Ship extends Alien {

    private int width;


    public Ship(Bitmap res,int _width){
        super(res,15, 15,_width,_width/200);
        width = _width;
        speed = width/100;
        radius = res.getWidth();
    }

    @Override
    public boolean disappear(){
        if(x > width - radius){
            return true;
        }else {
            return false;
        }
    }
}
