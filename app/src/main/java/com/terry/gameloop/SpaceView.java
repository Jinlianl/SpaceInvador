package com.terry.gameloop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;



/**
 * Created by liaojinliang on 16/4/2.
 */
public class SpaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Cannon cannon;
    private FireBullet bullet;
    private Ship ship;
    private ArrayList<Alien>  aliens = new ArrayList<>();
    private Bitmap aBm;//Bitmap for aliens;
    private Bitmap sBM;//Bitmap for spaceship;
    private Boolean launch;
    private int counter;
    private int curSpeed;




//    final Handler handler = new Handler();


    //Constructor
    public SpaceView(Context context) {
        super(context);
        //Notify the Surface holder to receive callback
        getHolder().addCallback(this);
        setFocusable(true);
        //initial game states here
        counter = 0;
        launch = false;
    }

    //declare game thread
    SpaceThread gameThread;


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //TODO construct init states
        cannon = new Cannon(BitmapFactory.decodeResource(getResources(), R.drawable.cannon_si),getWidth(),getHeight());
        int cW = getWidth()/10;
        aBm = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.alien_si),cW, cW,false);
        sBM = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mush_si), cW, cW, false);
        curSpeed = getWidth()/200;//initialize current speed
        makeAliens(aBm,curSpeed);


        //Launch Thread
        gameThread = new SpaceThread(this);
        gameThread.start();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //TODO respond to surface changes
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO stopping thread
        gameThread.interrupt();
    }


    private static final int left = -1;
    private static final int right = 1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            int x,y;

            x =(int) event.getX();
            y =(int) event.getY();
            // TODO: define the touch event
            if (y > getHeight()*2/3){ //touch on bottom side
                if (x < (getWidth()/2)){//left side
                    cannon.move(left);
                } else{ //right side
                    cannon.move(right);
                }
            }else{  //touch on top side
                if(bullet== null){
                    bullet = new FireBullet(cannon.getX(),cannon.getY());
                }

            }


            return true;
        }


        return super.onTouchEvent(event);
    }

    //todo:construct aliens
    private void makeAliens(Bitmap aBm, int _speed){
        int alien_w = getWidth()/8;
        int alien_h = getHeight()/12;
        for(int i = 0; i< 5; i++){
            for (int j = 1; j<5; j++){
                aliens.add(new Alien(aBm,15+i*alien_w,j*alien_h,getWidth(),_speed)) ;
            }
        }
    }
    private void makeSpaceShip(){
        if(launch){
            ship = new Ship(sBM,getWidth());
            launch = false;
        }
    }

    //todo:aliens moving strategy
    private void AlienMovingStrategy(){

        //formulate the moving speed;


        if(aliens.size() > 0){//alien exist

            //todo:detect the side of aliens

            if(aliens.get(0).bounceCheck() && aliens.get(0).getX() < getWidth()/2){
                for(int i =0; i <aliens.size(); i++){
                    aliens.get(i).SideUpdate(curSpeed);
                }
            }else if(aliens.get(aliens.size()-1).bounceCheck() && aliens.get(aliens.size()-1).getX() > getWidth()/2){
                for(int i =0; i <aliens.size(); i++){
                    aliens.get(i).SideUpdate(-curSpeed);
                }
            }


            for(int i =0; i <aliens.size(); i++){
                aliens.get(i).update();
                if(bullet!=null){
                    if(aliens.get(i).hitDetect(bullet.getX(), bullet.getY())){
                        bullet = null;
                        aliens.remove(i);
                    }
                }

            }


        }else {
            //todo:refresh new batch of aliens
            curSpeed = curSpeed*5/4;
            try {
                makeAliens(aBm,curSpeed);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //constantly update drawing status
    public void update() {
    //todo:keep updating in each frame
        counter++;
        if(counter == 500){
            launch = true;
            counter = 0;
        }
        if(bullet!=null) {
            bullet.update();
            if(bullet.disappear()){
                bullet = null;
            }
        }
        //apply aliens moving strategy here
        AlienMovingStrategy();

        if(ship!=null){
            ship.update();
            if(bullet!=null){
                if(ship.hitDetect(bullet.getX(),bullet.getY())){
                    ship = null;
                }
            }
            if(ship.disappear()){
                ship = null;
            }
        }else{
            makeSpaceShip();
        }

        int subCounter = counter%aliens.size();

        if(aliens.get(subCounter).getY() >= getHeight() - aBm.getHeight()){
            gameThread.interrupt();
        }


    }

    @Override
    public void draw(Canvas canvas){

        try {
            super.draw(canvas);
            canvas.drawColor(Color.BLACK);
            cannon.draw(canvas);

            if(bullet!=null) {
                bullet.draw(canvas);
            }
            if(aliens.size()>0){
                for(int i =0; i <aliens.size(); i++) {
                    if (aliens.get(i) != null) {
                        aliens.get(i).draw(canvas);
                    }
                }
            }
            if(ship!=null) {
                ship.draw(canvas);
            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
