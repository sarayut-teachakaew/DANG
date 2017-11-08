package com.adventurer.dang.Scenes;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Tiles.TileManager;


/**
 * Created by x_x on 4/11/2560.
 */

public class GameScene implements Scene {
    public static TextureButton CB_BUILD,CB_BUY,CB_CANCEL,CB_SELL,CB_UPGRADE;
    private SceneManager manager;
    private TileManager TileMan;
    private Point dragPev,dragCur;
    private Point posMDown,posMUP;
    private boolean dragBP = false;

    public GameScene(SceneManager manager){
        this.manager = manager;

        BitmapFactory bf = new BitmapFactory();
        CB_BUILD = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE
                ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_build));
        CB_BUY = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE
                ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_buy));
        CB_CANCEL = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE
                ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_cancel));
        CB_SELL = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE
                ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_sell));
        CB_UPGRADE = new TextureButton(new Point(0,0),Constants.CB_SIZE,Constants.CB_SIZE
                ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_upgrade));

        TileMan = new TileManager(10,10);

    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.rgb(250,240,230));
        TileMan.draw(canvas);
    }
    public void terminate(){
        manager.freeGame();
    }
    public void recieveTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(dragCur!=null)dragPev = new Point(dragCur);
                dragCur = new Point((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_DOWN:
                posMDown = new Point((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if(posMDown !=null)posMUP = new Point((int)event.getX(),(int)event.getY());
                dragPev = dragCur = null;
                dragBP = false;
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
                if(TileMan.getWidth()/2>Constants.SCREEN_WIDTH/3) {
                    if (Constants.DRAG_DIST.x > TileMan.getWidth() / 2 - Constants.SCREEN_WIDTH / 3)
                        Constants.DRAG_DIST.x = TileMan.getWidth() / 2 - Constants.SCREEN_WIDTH / 3;
                    if (Constants.DRAG_DIST.x < -TileMan.getWidth() / 2 + Constants.SCREEN_WIDTH / 3)
                        Constants.DRAG_DIST.x = -TileMan.getWidth() / 2 + Constants.SCREEN_WIDTH / 3;
                }else{
                    if (Constants.DRAG_DIST.x > TileMan.getWidth() / 4)
                        Constants.DRAG_DIST.x = TileMan.getWidth() / 4;
                    if (Constants.DRAG_DIST.x < -TileMan.getWidth() / 4)
                        Constants.DRAG_DIST.x = -TileMan.getWidth() / 4;
                }
                if (TileMan.getHeight()/2>Constants.SCREEN_HEIGHT/3) {
                    if (Constants.DRAG_DIST.y > TileMan.getHeight() / 2 - Constants.SCREEN_HEIGHT / 3)
                        Constants.DRAG_DIST.y = TileMan.getHeight() / 2 - Constants.SCREEN_HEIGHT / 3;
                    if (Constants.DRAG_DIST.y < -TileMan.getHeight() / 2 + Constants.SCREEN_HEIGHT / 3)
                        Constants.DRAG_DIST.y = -TileMan.getHeight() / 2 + Constants.SCREEN_HEIGHT / 3;
                }else{
                    if (Constants.DRAG_DIST.y > TileMan.getHeight() / 4)
                        Constants.DRAG_DIST.y = TileMan.getHeight() / 4;
                    if (Constants.DRAG_DIST.y < -TileMan.getHeight() / 4)
                        Constants.DRAG_DIST.y = -TileMan.getHeight() / 4;
                }
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

        if(!Backpack.click(CP))TileMan.click(panCP);
    }
}
