package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by x_x on 17/11/2560.
 */

public interface TileObject {
    void draw(Canvas canvas);
    Point getPos();
    void pushHp(float hp);
}
