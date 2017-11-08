package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Towers.AllTower;

/**
 * Created by x_x on 4/11/2560.
 */

public interface Tile {
    void draw(Canvas canvas);
    void open();
    void close();
    void update();
    boolean hitCheck(Point CP);
    void drawCB(Canvas canvas);
    Boolean clickCB(Point CP);
    boolean isOwnsTower();
    void createTower(AllTower tt);
    void delTower();
    public int getX();
    public int getY();
}
