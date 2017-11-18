package com.adventurer.dang;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.adventurer.dang.Scenes.SceneManager;

/**
 * Created by x_x on 1/11/2560.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private SceneManager sceneMan;
    private boolean isRunnig = false;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        sceneMan = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunnig = true;
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunnig = false;
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        sceneMan.recieveTouch(event);

        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {
        if(isRunnig) Backpack.update();
        sceneMan.update();

    }

    @Override
    public void draw(Canvas canvas) {
        if(isRunnig) {
            super.draw(canvas);
            sceneMan.draw(canvas);
            Backpack.draw(canvas);
        }
    }
}