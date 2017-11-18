package com.adventurer.dang.Buttons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by x_x on 17/11/2560.
 */

public class FullBar extends MidBar{

    public FullBar(float x,float y,int width,int height,float value,float maxValue,int color){super(x,y,width,height,value,maxValue,color);}
    public FullBar(Point pos,int width,int height,float value,float maxValue,int color){super(pos,width,height,value,maxValue,color);}
    public FullBar(float x,float y,int width,int height,int color){super(x,y,width,height,color);}
    public FullBar(Point pos,int width,int height,int color){super(pos,width,height,color);}
    public void draw(Canvas canvas){
        if(value<0)value=0;
        if(maxValue<=0)maxValue=value+1;
        int curW=(int)(value*width/maxValue);
        Rect rect = new Rect((int)(x-width/2),(int)(y-height/2),(int)(x-width/2+curW),(int)(y+height/2));
        Rect rect2 = new Rect((int)(x-width/2),(int)(y-height/2),(int)(x+width/2),(int)(y+height/2));

        Paint p = new Paint();p.setColor(Color.rgb(240,240,240));
        canvas.drawRect(rect2,p);
        canvas.drawRect(rect,paint);
    }
}
