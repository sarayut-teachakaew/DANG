package com.adventurer.dang;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Boukenshas.Boukensha;
import com.adventurer.dang.Boukenshas.Player;
import com.adventurer.dang.Scenes.GameScene;

/**
 * Created by x_x on 15/11/2560.
 */

public class Controller {
    protected Bitmap texture0,texture1,butt;
    protected int posX,posY,posBX,posBY;
    protected Rect rect,rectB;
    protected Boolean active = false;
    protected int type;

    public static int TYPE_MOVE =0,TYPE_FIRE=1;
    public static int SIZE = 400,BUTTON_SIZE=100;

    public Controller(int type){
        this.type = type;
        if(type== TYPE_MOVE) {
            texture0 = GameScene.pic.controller_move_unactive;
            texture1 = GameScene.pic.controller_move;
            butt = GameScene.pic.controller_button;

            posX = SIZE / 2;
            posY = Constants.SCREEN_HEIGHT - SIZE / 2;
            posBX = SIZE / 2;
            posBY = Constants.SCREEN_HEIGHT - SIZE / 2;
            rect = new Rect(posX - SIZE / 2, posY - SIZE / 2, posX + SIZE / 2, posY + SIZE / 2);
            rectB = new Rect(posBX - BUTTON_SIZE / 2, posBY - BUTTON_SIZE / 2, posBX + BUTTON_SIZE / 2, posBY + BUTTON_SIZE / 2);
        }
        if(type==TYPE_FIRE) {
            texture0 = GameScene.pic.controller_fire_unactive;
            texture1 = GameScene.pic.controller_fire;
            butt = GameScene.pic.controller_button;

            posX = Constants.SCREEN_WIDTH - SIZE / 2;
            posY = Constants.SCREEN_HEIGHT - SIZE / 2;
            posBX = Constants.SCREEN_WIDTH - SIZE / 2;
            posBY = Constants.SCREEN_HEIGHT - SIZE / 2;
            rect = new Rect(posX - SIZE / 2, posY - SIZE / 2, posX + SIZE / 2, posY + SIZE / 2);
            rectB = new Rect(posBX - BUTTON_SIZE / 2, posBY - BUTTON_SIZE / 2, posBX + BUTTON_SIZE / 2, posBY + BUTTON_SIZE / 2);
        }
    }
    public void open(){
        active = true;
    }
    public void close(Boukensha man){
        active = false;
        posBX =posX;
        posBY =posY;
        //if(type == TYPE_MOVE)man.capScreen();
        if(type == TYPE_FIRE)man.setAction(Player.ON_IDLE);
    }
    public void control(Boukensha man){
        if(active){
            if(type == TYPE_MOVE){
                man.move((posBX-posX)*man.getMoveSPD()/(SIZE/2),(posBY-posY)*man.getMoveSPD()/(SIZE/2));
                man.followScreen();
            }
            else if(type == TYPE_FIRE){
                man.setAction(Player.ON_AIM);
                man.setAimRot((float) Math.toDegrees(Math.atan2(posBY-posY, posBX-posX)));
                double rad = Math.sqrt((posBX-posX)*(posBX-posX)+(posBY-posY)*(posBY-posY));
                if(rad >SIZE*3/8){
                    man.setAction(Player.ON_FIRE);
                }
            }
        }
    }
    public boolean isActive(){return active;}
    public void setPosB(float x,float y){
        double rad = Math.sqrt((x-posX)*(x-posX)+(y-posY)*(y-posY));
        if(rad>SIZE/2){
            posBX=(int)(posX+((x-posX)*SIZE/2)/rad);
            posBY=(int)(posY+((y-posY)*SIZE/2)/rad);
        }else{
            posBX=(int)x;
            posBY=(int)y;
        }

        rectB = new Rect(posBX-BUTTON_SIZE/2,posBY-BUTTON_SIZE/2,posBX+BUTTON_SIZE/2,posBY+BUTTON_SIZE/2);
    }
    public boolean clickCheck(float x,float y){
        if((x-posX)*(x-posX)+(y-posY)*(y-posY)<(SIZE/2)*(SIZE/2))return true;
        else return false;
    }
    public boolean clickCheck(Point CP){
        return clickCheck(CP.x,CP.y);
    }
    public void draw(Canvas canvas){
        if(active){
            canvas.drawBitmap(texture1,null, rect,new Paint());
            canvas.drawBitmap(butt,null, rectB,new Paint());
        }
        else canvas.drawBitmap(texture0,null, rect,new Paint());
    }
}
