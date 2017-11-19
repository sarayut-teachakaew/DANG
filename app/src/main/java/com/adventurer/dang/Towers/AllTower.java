package com.adventurer.dang.Towers;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Card;
import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 6/11/2560.
 */

public interface AllTower extends TileObject {
    void draw(Canvas canvas);
    void open();
    void close();
    void update();
    Card getCard();
    void die();
    boolean hitCheck(Point CP);
    void drawCB(Canvas canvas);
    Boolean clickCB(Point CP);
}
