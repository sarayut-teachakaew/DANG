package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Constants;
import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 4/11/2560.
 */

public interface Tile extends TileObject {
    void draw(Canvas canvas);
    void open();
    void close();
    void update();
    boolean hitCheck(Point CP);
    void drawFloor(Canvas canvas);
    void drawCB(Canvas canvas);
    Boolean clickCB(Point CP);
    boolean isOwnsTower();
    boolean isWalkable();
    boolean createTower(AllTower tt);
    void delTower();
    public int getX();
    public int getY();
    public Point getPos();
    public AllTower getTower();
    public TileManager getTileMan();
}
