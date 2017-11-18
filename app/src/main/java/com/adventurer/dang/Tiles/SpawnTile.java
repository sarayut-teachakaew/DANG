package com.adventurer.dang.Tiles;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Boukenshas.Charger;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 17/11/2560.
 */

public class SpawnTile extends TextureButton implements Tile  {
    private TileManager tileMan;

    public SpawnTile(Point position, TileManager TM){
        super(position, Constants.TILE_SIZE,Constants.TILE_SIZE
                , GameScene.pic.spawn_tile);

        tileMan=TM;

    }

    public boolean isWalkable(){
        return true;
    }
    @Override
    public void draw(Canvas canvas) {

    }
    @Override
    public void drawFloor(Canvas canvas) {
        super.draw(canvas);
    }

    public void drawCB(Canvas canvas) {

    }
    public Boolean clickCB(Point CP){
        return false;
    }
    @Override
    public void update() {
        if(Math.random()<0.0001)tileMan.addBoukensha(new Charger(tileMan,getX(),getY()));
    }

    public void open(){

    }
    public void close(){

    }

    @Override
    public boolean isOwnsTower() {
        return false;
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