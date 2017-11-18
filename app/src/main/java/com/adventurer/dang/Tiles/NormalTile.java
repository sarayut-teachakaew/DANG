package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Boukenshas.Charger;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 4/11/2560.
 */

public class NormalTile extends TextureButton implements Tile {
    private TextureButton CirBut[];
    boolean onOpen = false,ownsTower=false;
    private AllTower tower;
    private boolean seen = true;
    private TileManager tileMan;

    public static int CB_BUILD=0,CB_WARP=1,CB_CANCEL=2;

    public NormalTile(Point position,TileManager TM){
        super(position,Constants.TILE_SIZE,Constants.TILE_SIZE
                ,GameScene.pic.normal_tile);

        tileMan=TM;


        CirBut = new TextureButton [] {GameScene.CB_BUILD,GameScene.CB_WARP,GameScene.CB_CANCEL};
    }

    public void setPointCB(){
        for(int i = 0;i<CirBut.length;i++){
            CirBut[i].setDestPos((i-(float)(CirBut.length-1)/2)* Constants.CB_SIZE+posX,posY-Constants.TILE_SIZE/2);
            CirBut[i].setStartPos(posX,posY);
        }
    }
    public boolean isWalkable(){
        if(ownsTower)return false;
        return true;
    }
    @Override
    public void draw(Canvas canvas) {
        if(seen)if(ownsTower)tower.draw(canvas);
    }
    @Override
    public void drawFloor(Canvas canvas) {
        if(seen)super.draw(canvas);
        else super.draw(canvas,GameScene.pic.unseen_tile);
    }

    public void drawCB(Canvas canvas) {
        if(seen)if(ownsTower)tower.drawCB(canvas);
        else if(onOpen) for(int i = 0;i<CirBut.length;i++)CirBut[i].draw(canvas);
    }
    public Boolean clickCB(Point CP){
        if(!ownsTower)for(int b = 0;b<CirBut.length;b++)if(CirBut[b].hitCheck(CP)){
            if(b==CB_BUILD && !ownsTower) Backpack.openBuild(this);
            if(b==CB_WARP){
                if(GameScene.MONEY<100)tileMan.alert.pop("Not Enougt Money");
                else {
                    tileMan.player.setPos(new Point(getX(),getY()));
                    GameScene.MONEY-=100;
                }
            }
            if(b==CB_CANCEL)tileMan.addBoukensha(new Charger(tileMan,getX(),getY()));
            close();
            return true;
        }
        if(ownsTower)return tower.clickCB(CP);
        else return false;
    }
    public boolean createTower(AllTower tt){
        if(GameScene.MONEY<100){
            tileMan.alert.pop("Not Enougt Money");
            Backpack.close();
            return false;
        }else if(tileMan.placeCheck(this)) {
            tower = tt;
            ownsTower = true;
            Backpack.close();
            GameScene.MONEY-=100;
            return true;
        }
        else {
            tileMan.alert.pop("Can't Place!");
            return false;
        }
    }
    @Override
    public void update() {
        if(((getX()-tileMan.player.getX())*(getX()-tileMan.player.getX())+(getY()-tileMan.player.getY())*(getY()-tileMan.player.getY()))
                >(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE)*(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE)){
            seen=false;
            close();
            if(Backpack.getSTile()==this)Backpack.close();
        }
        else seen = true;
        if(tower!=null)tower.update();
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

    @Override
    public AllTower getTower() {
        return tower;
    }

    @Override
    public void pushHp(float hp) {

    }
    @Override
    public TileManager getTileMan() {
        return tileMan;
    }
}
