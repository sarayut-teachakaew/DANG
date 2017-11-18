package com.adventurer.dang;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by x_x on 5/11/2560.
 */

public class Text {
    public Point destPos,rotDis=new Point(0,0);
    public float posX,posY;
    public double timeSec,timeStart;
    public boolean isImmortal = true;
    private String txt=null;
    private Paint paint;
    private int distY=0;
    public Text(Point curPos,Point destPos, String txt, int textColor, int textSize,float timeSec){
        this(curPos,destPos, txt, textColor, textSize);
        isImmortal = false;
        this.timeSec = timeSec;
    }
    public Text(Point position, String txt, int textColor, int textSize){
        this(position,position,txt,textColor,textSize);
    }
    public Text(Point position, String txt, int textColor,float timeSec){
        this(position, txt, textColor, 50);
        isImmortal = false;
        this.timeSec = timeSec;
    }
    public Text(Point position, String txt, int textColor){
        this(position, txt, textColor, 50);
    }
    public Text(Point curPos,Point destPos, String txt, int textColor, int textSize){
        posX = curPos.x;posY=curPos.y;
        this.destPos = destPos;
        this.txt = txt;
        paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        timeStart = System.currentTimeMillis();
    }
    public Text(Point curPos,Point destPos, String txt, int textColor, int textSize,int distY){
        this(curPos,destPos,txt,textColor,textSize);
        this.distY=distY;
    }
    public Text(Point curPos,Point destPos, String txt, int textColor){
        this(curPos,destPos, txt, textColor, 50);
    }
    public boolean stayAlive(){
        if(isImmortal)return true;
        else if (timeSec<=0&&posX == destPos.x && posY ==destPos.y)return false;
        else return true;
    }
    public void draw(Canvas canvas){
        if(timeStart<Constants.INIT_TIME)timeStart=Constants.INIT_TIME;
        timeSec -=(System.currentTimeMillis()-timeStart)/1000;
        timeStart = System.currentTimeMillis();
        if (posX != destPos.x || posY !=destPos.y) {
            posX += (destPos.x-posX)/6;
            posY += (destPos.y-posY)/6;
            if ((posX - destPos.x) * (posX - destPos.x) + (posY - destPos.y) * (posY - destPos.y) < 1.4)
            {posX = destPos.x;posY = destPos.y;}
        }
        if(txt!=null){
            paint.setTextAlign(Paint.Align.CENTER);
            Rect result = new Rect();
            paint.getTextBounds(txt, 0, txt.length(), result);
            canvas.drawText(txt,(int) posX,(int) posY+result.height()/2+distY, paint);
        }
    }
    public void drawRotate(Canvas canvas){
        if(rotDis.x==0&&rotDis.y==0){
            draw(canvas);return;
        }
        canvas.save();
        float rot = (float) Math.toDegrees(Math.atan2(rotDis.y, rotDis.x));
        //System.out.println(rot);
        canvas.rotate(rot+90,posX,posY);
        draw(canvas);
        canvas.restore();
    }
    public void setPos(Point pos){
        posX=pos.x;
        posY=pos.y;
        destPos=new Point(pos);
    }
}
