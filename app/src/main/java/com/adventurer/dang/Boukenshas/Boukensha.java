package com.adventurer.dang.Boukenshas;

import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 15/11/2560.
 */

public interface Boukensha extends TileObject {
    void update();
    void draw(Canvas canvas);
    void move(float x,float y);
    void pushForce(float x,float y);
    void setAction(int action);
    void setAimRot(float aimRot);
    void setPos(Point pos);
    void pushHp(float value);
    void die();
    void capScreen();
    void followScreen();
    void getAttention(Point ap);
    Point getPos();
    int getX();
    int getY();
    int getWidth();
    float getMoveSPD();
    boolean isFriendly();
    float getHp();
}
