package com.adventurer.dang.Tiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Towers.MoneyTower;
import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 4/11/2560.
 */

public class NormalTile extends TextureButton implements Tile {
    private TextureButton CirBut[];
    boolean onOpen = false,ownsTower=false;
    private AllTower tower;
    private TileManager tileMan;

    public static int CB_BUILD=0,CB_CANCEL=1;

    public NormalTile(Point position,TileManager TM){
        super(position,Constants.TILE_SIZE,Constants.TILE_SIZE
                ,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tile0));

        tileMan=TM;


        CirBut = new TextureButton [] {GameScene.CB_BUILD,GameScene.CB_CANCEL};
    }

    public void setPointCB(){
        for(int i = 0;i<CirBut.length;i++){
            CirBut[i].setDestPos((i-(float)(CirBut.length-1)/2)* Constants.CB_SIZE+posX,posY-Constants.TILE_SIZE/2);
            CirBut[i].setStartPos(posX,posY);
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(ownsTower)tower.draw(canvas);
    }

    public void drawCB(Canvas canvas) {
        if(ownsTower)tower.drawCB(canvas);
        else if(onOpen) for(int i = 0;i<CirBut.length;i++)CirBut[i].draw(canvas);
    }
    public Boolean clickCB(Point CP){
        for(int b = 0;b<CirBut.length;b++)if(CirBut[b].hitCheck(CP)){
            if(b==CB_BUILD && !ownsTower) Backpack.openBuild(this);
            close();
            return true;
        }
        if(ownsTower)return tower.clickCB(CP);
        else return false;
    }
    public void createTower(AllTower tt){
        tower = tt;
        ownsTower = true;
        Backpack.close();
    }
    @Override
    public void update() {

    }

    public void open(){
        if(ownsTower)tower.open();
        else {
            setPointCB();
            onOpen = true;
        }
    }
    public void close(){
        if(ownsTower)tower.close();
        onOpen = false;
    }

    @Override
    public boolean isOwnsTower() {
        return ownsTower;
    }

    @Override
    public void delTower() {
        ownsTower = false;
        tower = null;
    }
}
