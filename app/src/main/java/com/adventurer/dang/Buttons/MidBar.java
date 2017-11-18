package com.adventurer.dang.Buttons;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


/**
 * Created by x_x on 17/11/2560.
 */

public class MidBar {
    public float value,maxValue;
    public float x,y;
    public int width,height;
    protected Paint paint;

    public MidBar(float x,float y,int width,int height,float value,float maxValue,int color){
        this.x=x;this.y=y;
        this.width=width;this.height=height;
        this.value=value;this.maxValue=maxValue;
        paint = new Paint();
        paint.setColor(color);
    }
    public MidBar(Point pos,int width,int height,float value,float maxValue,int color){
        this(pos.x,pos.y,width,height,value,maxValue,color);
    }
    public MidBar(float x,float y,int width,int height,int color){
        this.x=x;this.y=y;
        this.width=width;this.height=height;
        value=1;maxValue=1;
        paint = new Paint();
        paint.setColor(color);
    }
    public MidBar(Point pos,int width,int height,int color){
        this(pos.x,pos.y,width,height,color);
    }
    public void setValue(float value,float maxValue){
        this.value=value;
        this.maxValue=maxValue;
    }
    public void draw(Canvas canvas,float value,float maxValue){
        setValue(value, maxValue);
        draw(canvas);
    }

    public void draw(Canvas canvas){
        if(value<0)value=0;
        if(maxValue<=0)maxValue=value+1;
        int curW=(int)(value*width/maxValue);
        Rect rect = new Rect((int)(x-curW/2),(int)(y-height/2),(int)(x+curW/2),(int)(y+height/2));

        canvas.drawRect(rect,paint);
    }
    public void setPos(float x,float y){
        this.x=x;this.y=y;
    }
    public void setPos(Point pos){
        setPos(pos.x,pos.y);
    }
}
