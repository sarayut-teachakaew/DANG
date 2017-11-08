package com.adventurer.dang.Buttons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


/**
 * Created by x_x on 4/11/2560.
 */

public class RectButton implements MyButton{
    private Rect rect ;
    private int width,height,rectColor;
    private boolean color = true;
    private Point pos;
    private String text=null;
    private Paint paint;
    public RectButton(float width, float height, Point position, int rectColor){
        this.width = (int)width;
        this.height = (int)height;
        this.pos = position;
        this.rectColor = rectColor;
        rect = new Rect(pos.x-this.width/2,pos.y-this.height/2,pos.x+this.width/2,pos.y+this.height/2);
        paint = new Paint();
    }
    public RectButton(float width, float height, Point position, int rectColor, String text, int textColor){
        this(width,height,position,rectColor,text,textColor,100);
    }
    public RectButton(float width, float height, Point position, int rectColor, String text, int textColor, int textSize){
        this(width,height,position,rectColor);
        this.text = text;
        paint.setColor(textColor);
        paint.setTextSize(textSize);
    }
    public RectButton(float width, float height, Point position, boolean rectColor){
        this.width = (int)width;
        this.height = (int)height;
        this.pos = position;
        color = rectColor;
        rect = new Rect(pos.x-this.width/2,pos.y-this.height/2,pos.x+this.width/2,pos.y+this.height/2);
        paint = new Paint();
    }
    public RectButton(float width, float height, Point position, boolean rectColor, String text, int textColor){
        this(width,height,position,rectColor,text,textColor,100);
    }
    public RectButton(float width, float height, Point position, boolean rectColor, String text, int textColor, int textSize){
        this(width,height,position,rectColor);
        this.text = text;
        paint.setColor(textColor);
        paint.setTextSize(textSize);
    }
    public void draw(Canvas canvas){
        if(color){
            Paint p = new Paint();
            //p.setStyle(Paint.Style.STROKE);
            //p.setStrokeWidth(5);
            p.setColor(rectColor);
            canvas.drawRect(rect, p);
        }

        if(text!=null){
            paint.setTextAlign(Paint.Align.CENTER);
            Rect result = new Rect();
            paint.getTextBounds(text, 0, text.length(), result);
            canvas.drawText(text, pos.x, pos.y+result.height()/2, paint);
        }
    }
    public boolean hitCheck(Point CP, Rect hitBox){
        if (hitBox.contains((int)CP.x,(int)CP.y)){rectColor=Color.WHITE;return true;}
        return false;
    }
    public boolean hitCheck(Point CP){
        return hitCheck(CP,rect);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
