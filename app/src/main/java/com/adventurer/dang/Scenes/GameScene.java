package com.adventurer.dang.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Controller;
import com.adventurer.dang.Pic;
import com.adventurer.dang.Text;
import com.adventurer.dang.Tiles.TileManager;


/**
 * Created by x_x on 4/11/2560.
 */

public class GameScene implements Scene {
    public static TextureButton CB_BUILD,CB_WARP,CB_BUY,CB_CANCEL,CB_SELL,CB_UPGRADE;
    public static Pic pic;
    public static float MONEY=1000;
    private SceneManager manager;
    private Controller ctrMove,ctrFire;
    private int CMIndex=-1,CFIndex=-1;
    private TileManager tileMan;
    private Point dragPev,dragCur;
    private Point posMDown,posMUP;
    private boolean dragBP = false;

    public GameScene(SceneManager manager){
        pic = new Pic(manager.GAME_SCENE);
        this.manager = manager;

        ctrMove=new Controller(Controller.TYPE_MOVE);
        ctrFire=new Controller(Controller.TYPE_FIRE);

        CB_BUILD = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_build);
        CB_WARP = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_warp);
        CB_BUY = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_buy);
        CB_CANCEL = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_cancel);
        CB_SELL = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_sell);
        CB_UPGRADE = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE,pic.cb_upgrade);

        tileMan = new TileManager(50,50);
        tileMan.player.capScreen();

        MONEY=1000;

    }
    public void update(){
        if(tileMan.player.getHp()<=0){
            manager.activeScene = SceneManager.MENU_SCENE;
            terminate();
        }
        ctrMove.control(tileMan.player);
        ctrFire.control(tileMan.player);
        tileMan.update();
    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.rgb(250,240,230));
        tileMan.draw(canvas);
        ctrMove.draw(canvas);
        ctrFire.draw(canvas);

        Text moneyTxt = new Text(new Point((int)(Constants.SCREEN_WIDTH/2),(int)(Constants.SCREEN_HEIGHT-Constants.SCREEN_SCALE*80))
        ,(int)MONEY+" G",Color.rgb(255,215,0),Constants.SCREEN_SCALE*100);
        moneyTxt.draw(canvas);
    }
    public void terminate(){
        Backpack.close();
        tileMan.delAllTower();
        manager.freeGame();
        Constants.DRAG_DIST=new Point(0,0);
    }
    public void recieveTouch(MotionEvent event){
        if(ctrMove.isActive()&&ctrFire.isActive()&&event.getActionMasked()==MotionEvent.ACTION_POINTER_UP){
            if(CMIndex==event.getActionIndex()){
                ctrMove.close(tileMan.player);CMIndex=-1;
            }
            if(CFIndex==event.getActionIndex()){
                ctrFire.close(tileMan.player);CFIndex=-1;
            }
        }
        if(ctrMove.isActive()&&ctrFire.isActive()&&event.getAction()==MotionEvent.ACTION_MOVE){
            int pointerCount = event.getPointerCount();
            for(int i = 0; i < pointerCount; ++i)
            {
                int pointerIndex = i;
                int pointerId = event.getPointerId(pointerIndex);
                //Log.d("pointer id - move",Integer.toString(pointerId));
                try{
                if(CMIndex==pointerId){
                    ctrMove.open();
                    ctrMove.setPosB(event.getX(CMIndex),event.getY(CMIndex));
                }
                if(CFIndex==pointerId){
                    ctrFire.open();
                    ctrFire.setPosB(event.getX(CFIndex),event.getY(CFIndex));
                }}catch (Exception e){
                    System.out.println("Error");
                }
            }
        }
        else if(event.getActionIndex()==0){
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(ctrMove.isActive()&& event.getX()>Constants.SCREEN_WIDTH/2){
                    ctrFire.open();
                    CFIndex=1-CMIndex;
                }
                if(ctrFire.isActive()&& event.getX()<=Constants.SCREEN_WIDTH/2){
                    ctrMove.open();
                    CMIndex=1-CFIndex;
                }
                if(ctrMove.isActive())ctrMove.setPosB(event.getX(),event.getY());
                else if(ctrFire.isActive())ctrFire.setPosB(event.getX(),event.getY());
                else {
                    if (dragCur != null) dragPev = new Point(dragCur);
                    dragCur = new Point((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(ctrMove.clickCheck(event.getX(),event.getY())){
                    //tileMan.close();
                    ctrMove.open();
                    ctrMove.setPosB(event.getX(),event.getY());
                    CMIndex=event.getActionIndex();
                }
                else if(ctrFire.clickCheck(event.getX(),event.getY())){
                    //tileMan.close();
                    ctrFire.open();
                    ctrFire.setPosB(event.getX(),event.getY());
                    CFIndex=event.getActionIndex();
                }
                else posMDown = new Point((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                ctrMove.close(tileMan.player);CMIndex=-1;
                ctrFire.close(tileMan.player);CFIndex=-1;

                if(posMDown !=null)posMUP = new Point((int)event.getX(),(int)event.getY());
                dragPev = dragCur = null;
                dragBP = false;
                break;
        }}else if(ctrMove.isActive()&&event.getActionIndex()==1){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_MOVE:
                    if(ctrFire.isActive())ctrFire.setPosB(event.getX(1),event.getY(1));
                    else if(!ctrMove.clickCheck(event.getX(1),event.getY(1))) {
                        if (dragCur != null) dragPev = new Point(dragCur);
                        dragCur = new Point((int) event.getX(1), (int) event.getY(1));
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if(ctrFire.clickCheck(event.getX(1),event.getY(1))){
                        //tileMan.close();
                        ctrFire.open();
                        ctrFire.setPosB(event.getX(1),event.getY(1));
                        CFIndex=1-CMIndex;
                    }
                    else if(!ctrMove.clickCheck(event.getX(1),event.getY(1)))posMDown = new Point((int)event.getX(1),(int)event.getY(1));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    ctrFire.close(tileMan.player);CFIndex=-1;

                    if(posMDown !=null)posMUP = new Point((int)event.getX(1),(int)event.getY(1));
                    dragPev = dragCur = null;
                    dragBP = false;
                    break;
            }
        }else if(ctrFire.isActive()&&event.getActionIndex()==1){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_MOVE:
                    if(ctrMove.isActive())ctrMove.setPosB(event.getX(1),event.getY(1));
                    else if(!ctrFire.clickCheck(event.getX(1),event.getY(1))) {
                        if (dragCur != null) dragPev = new Point(dragCur);
                        dragCur = new Point((int) event.getX(1), (int) event.getY(1));
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if(ctrMove.clickCheck(event.getX(1),event.getY(1))){
                        //tileMan.close();
                        ctrMove.open();
                        ctrMove.setPosB(event.getX(1),event.getY(1));
                        CMIndex=1-CFIndex;
                    }
                    else if(!ctrFire.clickCheck(event.getX(1),event.getY(1)))posMDown = new Point((int)event.getX(1),(int)event.getY(1));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    ctrMove.close(tileMan.player);CMIndex=-1;

                    if(posMDown !=null)posMUP = new Point((int)event.getX(1),(int)event.getY(1));
                    dragPev = dragCur = null;
                    dragBP = false;
                    break;
            }
        }


        if (dragPev !=null && dragCur != null){
            if(Backpack.isOpen() && (Backpack.getWidth() / 2 > Constants.SCREEN_WIDTH / 3)&& (dragBP||event.getY()<Constants.CARD_HEIGHT)){
                dragBP =true;
                Backpack.dragX += dragCur.x - dragPev.x;
                if (Backpack.dragX > Backpack.getWidth() / 2 - Constants.SCREEN_WIDTH / 3)
                    Backpack.dragX = Backpack.getWidth() / 2 - Constants.SCREEN_WIDTH / 3;
                if (Backpack.dragX < -Backpack.getWidth() / 2 + Constants.SCREEN_WIDTH / 3)
                    Backpack.dragX = -Backpack.getWidth() / 2 + Constants.SCREEN_WIDTH / 3;
            }
            else {
                Constants.DRAG_DIST.x += dragCur.x - dragPev.x;
                Constants.DRAG_DIST.y += dragCur.y - dragPev.y;
                if (Constants.DRAG_DIST.x < -(tileMan.getWidth()+ tileMan.getWidth() / 4)+Constants.SCREEN_WIDTH)
                    Constants.DRAG_DIST.x = -(tileMan.getWidth()+ tileMan.getWidth() / 4)+Constants.SCREEN_WIDTH;
                if (Constants.DRAG_DIST.x > tileMan.getWidth() / 4)
                    Constants.DRAG_DIST.x = tileMan.getWidth() / 4;
                if (Constants.DRAG_DIST.y < -(tileMan.getHeight()+tileMan.getHeight() / 4)+Constants.SCREEN_HEIGHT)
                    Constants.DRAG_DIST.y = -(tileMan.getHeight()+tileMan.getHeight() / 4)+Constants.SCREEN_HEIGHT;
                if (Constants.DRAG_DIST.y > tileMan.getHeight() / 4)
                    Constants.DRAG_DIST.y = tileMan.getHeight() / 4;
            }
        }
        if(posMDown !=null && posMUP != null) {
            if ((posMUP.x - posMDown.x)*(posMUP.x - posMDown.x) + (posMUP.y - posMDown.y)*(posMUP.y - posMDown.y) <= 200)
                click(new Point(posMUP.x , posMUP.y));
            posMDown = posMUP = null;
        }
    }
    public void click(Point CP){
        Point panCP=new Point(CP.x - Constants.DRAG_DIST.x, CP.y - Constants.DRAG_DIST.y);

        if(!Backpack.click(CP)) tileMan.click(panCP);
    }
}
