package com.adventurer.dang.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.adventurer.dang.Balloon;
import com.adventurer.dang.Buttons.RectButton;
import com.adventurer.dang.Constants;

/**
 * Created by x_x on 4/11/2560.
 */

public class MenuScene implements Scene {
    private SceneManager manager;
    private RectButton playButton;
    Balloon ball;
    public MenuScene(SceneManager manager){
        this.manager = manager;
        setButton();
        ball = new Balloon();
    }
    public void setButton(){
        playButton = new RectButton(300,200,new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2),Color.rgb(176,196,222),"PLAY",Color.rgb(240,255,255));
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.rgb(245,255,250));
        playButton.draw(canvas);
        ball.draw(canvas);
    }
    public void terminate(){
        setButton();
    }
    public void recieveTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ball.setPos(event.getX(),event.getY());
                ball.pop("POP");
                //ball.speak("ไม่อยากกินต้มไก่");
                break;
            case MotionEvent.ACTION_UP:
                if(playButton.hitCheck(new Point((int)event.getX(),(int)event.getY()))) {
                    manager.reGame();
                    manager.ACTIVE_SCENE = 1;
                    terminate();
                }
        }
    }
}
