package com.adventurer.dang.Buttons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Constants;

/**
 * Created by x_x on 4/11/2560.
 */

public class TextureButton implements MyButton{
    protected Bitmap texture;
    protected Point destPos;
    protected float posX,posY;
    protected Rect rect;
    public int width,height;
    protected boolean panPic = true;
    public TextureButton(Point destPos,int width,int height,Bitmap texture,Point startPos){
        posX = startPos.x;posY = startPos.y;
        this.destPos=destPos;
        this.width =width;this.height = height;
        rect = new Rect((int)posX- width/2,(int)posY-height/2
                ,(int)posX+width/2,(int)posY+height/2);
        this.texture = texture;
    }
    public void setPos(Point position){
        posX = position.x;posY = position.y;
        this.destPos = new Point(position);
    }
    public void setPos(float x,float y){
        posX = x;posY = y;this.destPos = new Point((int)x,(int)y);
    }
    public void setPosX(float x){
        this.destPos = new Point((int)x,destPos.y);posX = x;
        rect = new Rect((int)posX- width/2,(int)posY-height/2
                ,(int)posX+width/2,(int)posY+height/2);
    }
    public void setPosY(float y){
        this.destPos = new Point(destPos.x,(int)y);posY = y;
        rect = new Rect((int)posX- width/2,(int)posY-height/2
                ,(int)posX+width/2,(int)posY+height/2);
    }
    public void setStartPos(Point startPos){
        posX = startPos.x;posY = startPos.y;
    }
    public void setStartPos(float posX,float posY){
        this.posX = posX;this.posY = posY;
    }
    public void setDestPos(Point destPos){
        this.destPos = new Point(destPos);
    }
    public void setDestPos(float posX,float posY){
        this.destPos = new Point((int)posX,(int)posY);
    }
    public void setDestPosX(float x){
        this.destPos = new Point((int)x,destPos.y);
    }
    public void setDestPosY(float y){this.destPos = new Point(destPos.x,(int)y);}
    public int getX(){return destPos.x;}
    public int getY(){return destPos.y;}
    public Point getPos(){return new Point(destPos);}
    public TextureButton(Point position,int width,int height,Bitmap texture){
        this(position,width,height,texture,position);
    }
    public void draw(Canvas canvas){
        draw(canvas,texture);
    }
    public void draw(Canvas canvas,Bitmap newPic){
        if (posX != destPos.x || posY !=destPos.y) {
            posX += (destPos.x-posX)/5;
            posY += (destPos.y-posY)/5;
            if ((posX - destPos.x) * (posX - destPos.x) + (posY - destPos.y) * (posY - destPos.y) < 5)
            {posX = destPos.x;posY = destPos.y;}
            rect = new Rect((int)posX- width/2,(int)posY-height/2
                    ,(int)posX+width/2,(int)posY+height/2);
        }
        if(panPic){
            Rect panR = new Rect(rect.left+Constants.DRAG_DIST.x,rect.top+Constants.DRAG_DIST.y
                    ,rect.right+Constants.DRAG_DIST.x,rect.bottom+Constants.DRAG_DIST.y);
            if(panR.left<Constants.SCREEN_WIDTH&&panR.top<Constants.SCREEN_HEIGHT&&panR.right>0&&panR.bottom>0)
                canvas.drawBitmap(newPic,null, panR,new Paint());
        }
        else canvas.drawBitmap(newPic,null, rect,new Paint());
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
    public boolean hitCheck(Point CP, Rect hitBox){
        if (hitBox.contains((int)CP.x,(int)CP.y))return true;
        return false;
    }
    public boolean hitCheck(Point CP){
        return hitCheck(CP,rect);
    }
    public boolean isDest(){return posX == destPos.x && posY == destPos.y;}
}
