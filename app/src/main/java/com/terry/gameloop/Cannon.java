package com.terry.gameloop;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by liaojinliang on 16/4/2.
 */
public class Cannon {
    private Bitmap cannonImg;
    private float x,y;//coordinates
    private int height,width;
    private int moveVector;
    private int cW,cH;

    private static final int left = -1;
    private static final int right = 1;

    public Cannon(Bitmap res, int w, int h){
        cannonImg = res;
        width = w;
        height = h;
        cW = cannonImg.getWidth();
        cH = cannonImg.getHeight();
        x = w /2 - cW/2;
        y = h - cH;
        moveVector = cW/2;
    }



    public void move(int side){


        if(side == left){
            if(x >= moveVector){
                x+= left*moveVector;
            }else {
                x = 0;
            }
        }

        if(side == right){
            if(x <= width - cannonImg.getWidth() - moveVector){
                x+= right*moveVector;
            }else {
                x = width - cannonImg.getWidth();
            }
        }

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(cannonImg,x,y,null);
        if(x<0)
        {
            canvas.drawBitmap(cannonImg, x+ width, y, null);
        }
    }

    //get the cannon's current position
    public float getX(){
        return x + cW/2;
    }//get the center of cannon

    public float getY(){
        return y;
    }

}
