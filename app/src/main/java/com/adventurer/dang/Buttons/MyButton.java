package com.adventurer.dang.Buttons;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by x_x on 4/11/2560.
 */

public interface MyButton {
    void draw(Canvas canvas);
    void open();
    void close();
    boolean hitCheck(Point CP, Rect hitBox);
}
