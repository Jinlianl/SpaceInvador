package com.terry.gameloop;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by liaojinliang on 16/4/2.
 */
public class SpaceThread extends Thread{
    private SpaceView spaceView;

    public SpaceThread(SpaceView spaceView) {
        super();
        this.spaceView = spaceView;
    }



    @Override
    public void run() {
        int FPS = 30;
        long timeMillis;
        long waitTime;
        long startTime;
        long targetTime = 1000/FPS;
        SurfaceHolder surfaceHolder = spaceView.getHolder();
        // Main game loop.
        while( !this.interrupted() ) {
        //You might want to do game specific processing in a method you call here
            startTime = System.nanoTime();
            Canvas canvas = surfaceHolder.lockCanvas(null);
            try {
                synchronized(surfaceHolder) {
                    spaceView.update();
                    spaceView.draw(canvas);

                }
            } catch (Exception e) {
            } finally {
                if ( canvas != null ) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        // Set the frame rate by setting this delay
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;
            try {
                if (waitTime > 0){
                    this.sleep(waitTime);
                }
            } catch (InterruptedException e) {
        // Thread was interrupted while sleeping.
                return;
            }

        }
    }
}
