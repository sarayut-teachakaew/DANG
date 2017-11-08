package com.adventurer.dang.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by x_x on 3/11/2560.
 */

public interface Scene {
    void update();
    void draw(Canvas canvas);
    void terminate();
    void recieveTouch(MotionEvent event);
}
