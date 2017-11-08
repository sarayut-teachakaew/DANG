package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Constants;

/**
 * Created by x_x on 4/11/2560.
 */

public class TileManager {
    private Tile table[][];
    public int x,y;
    private Tile curTile=null;
    public TileManager(int x,int y){
        this.x = x;this.y = y;
        table = new Tile[y][x];
        for(int i=0;i<y;i++)for(int j=0;j<x;j++){
            Point p = new Point(((j-(x-1)/2)* Constants.TILE_SIZE)+Constants.SCREEN_WIDTH/2
                    ,((i-(y-1)/2)*Constants.TILE_SIZE)+Constants.SCREEN_HEIGHT/2);
            if(Math.random()>0.3)table[i][j]=new NormalTile(p,this);
            else table[i][j]=new BoulderTile(p,this);
        }
    }
    public void draw(Canvas canvas){
        for(int i=0;i<y;i++)for(int j=0;j<x;j++)table[i][j].draw(canvas);
        if(curTile != null)curTile.drawCB(canvas);
    }
    public void click(Point CP){
        if(curTile != null&&curTile.clickCB(CP))curTile=null;
        else {
            curTile=null;
            for(int i=0;i<y;i++)for(int j=0;j<x;j++)
                if(table[i][j].hitCheck(CP)) {
                    table[i][j].open();
                    curTile = table[i][j];
                }
                else table[i][j].close();
        }
    }
    public void changeTile(Tile bef,Tile aft){
        for(int i=0;i<y;i++)for(int j=0;j<x;j++)if(table[i][j] == bef){
            table[i][j] = aft;
            return;
        }
    }
    public int getWidth(){
        return x*Constants.TILE_SIZE;
    }
    public int getHeight(){
        return y*Constants.TILE_SIZE;
    }

}
