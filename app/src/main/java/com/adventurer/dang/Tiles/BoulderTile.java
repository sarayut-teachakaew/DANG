package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 7/11/2560.
 */

public class BoulderTile extends TextureButton implements Tile  {
    private TextureButton CirBut[];
    boolean onOpen = false,ownsTower=false;
    private TileManager tileMan;
    private boolean seen = true;

    public static int CB_BUY=0,CB_CANCEL=1;

    public BoulderTile(Point position,TileManager TM){
        super(position, Constants.TILE_SIZE+5,Constants.TILE_SIZE+5
                ,GameScene.pic.randomBoulderTile());

        tileMan=TM;

        CirBut = new TextureButton [] {GameScene.CB_BUY,GameScene.CB_CANCEL};
    }

    public void setPointCB(){
        for(int i = 0;i<CirBut.length;i++){
            CirBut[i].setDestPos((i-(float)(CirBut.length-1)/2)* Constants.CB_SIZE+posX,posY-Constants.TILE_SIZE/2);
            CirBut[i].setStartPos(posX,posY);
        }
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void drawFloor(Canvas canvas) {
        if(seen)super.draw(canvas);
        else super.draw(canvas,GameScene.pic.unseen_tile);
    }

    public void drawCB(Canvas canvas) {
        if(onOpen) for(int i = 0;i<CirBut.length;i++)CirBut[i].draw(canvas);
    }
    public Boolean clickCB(Point CP){
        for(int b = 0;b<CirBut.length;b++)if(CirBut[b].hitCheck(CP)){
            if(b==CB_BUY)tileMan.changeTile(this,new NormalTile(new Point(getX(),getY()),tileMan));
            close();
            return true;
        }
        return false;
    }
    @Override
    public void update() {
        if(((getX()-tileMan.player.getX())*(getX()-tileMan.player.getX())+(getY()-tileMan.player.getY())*(getY()-tileMan.player.getY()))
                >(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE)*(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE)){
            seen=false;
            close();
        }
        else seen = true;
    }

    public void open(){
        setPointCB();
        onOpen = true;
    }
    public void close(){
        onOpen = false;
    }

    @Override
    public boolean isOwnsTower() {
        return ownsTower;
    }

    @Override
    public void delTower() {

    }
    @Override
    public boolean createTower(AllTower tt) {
        return false;
    }

    @Override
    public AllTower getTower() {
        return null;
    }

    @Override
    public void pushHp(float hp) {

    }

    @Override
    public TileManager getTileMan() {
        return tileMan;
    }
}
