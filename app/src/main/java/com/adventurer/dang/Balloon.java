package com.adventurer.dang;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by x_x on 5/11/2560.
 */

public class Balloon {
    private ArrayList<Text> texts = new ArrayList<>();
    private ArrayList<Text> delT = new ArrayList<>();
    private ArrayList<Text> addT = new ArrayList<>();
    private ArrayList<Text> movetexts = new ArrayList<>();
    private ArrayList<Text> addMT = new ArrayList<>();
    public Point pos =new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2)
            ,dist = new Point(0,Constants.SCREEN_SCALE*-100);
    public int color = Color.BLACK, size = 50;
    public int upSpeak = -40;
    public float timePop=1,timeSpeak=3;
    public Boolean popRotate = true;
    public Point ranPop = new Point(Constants.SCREEN_SCALE*100,Constants.SCREEN_SCALE*30);

    public void pop(String txt){
        if (popRotate){
            Point rotDis = new Point((int)(dist.x-ranPop.x+Math.random()*ranPop.x*2)
                    ,(int)(dist.y-ranPop.y+Math.random()*ranPop.y*2));
            //rotDis = new Point(-60,-60);
            Text rotT = new Text(pos,new Point(rotDis.x+pos.x,rotDis.y+pos.y),txt, color,size,timePop);
            rotT.rotDis = new Point(rotDis);
            addT.add(rotT);
        }
        else addT.add(new Text(pos,new Point(pos.x+dist.x,pos.y+dist.y),txt, color,size,timePop));
    }
    public void pop(String txt,int color){
        if (popRotate){
            Point rotDis = new Point((int)(dist.x-ranPop.x+Math.random()*ranPop.x*2)
                    ,(int)(dist.y-ranPop.y+Math.random()*ranPop.y*2));
            //rotDis = new Point(-60,-60);
            Text rotT = new Text(pos,new Point(rotDis.x+pos.x,rotDis.y+pos.y),txt, color,size,timePop);
            rotT.rotDis = new Point(rotDis);
            addT.add(rotT);
        }
        else addT.add(new Text(pos,new Point(pos.x+dist.x,pos.y+dist.y),txt, color,size,timePop));
    }
    public void addMoveText(String txt, int dist){
        Point sp = new Point(pos.x,pos.y);
        addMT.add(new Text(sp,sp,txt, color,size,dist));
    }
    public void speak(String txt){
        Point sp = new Point(pos.x,pos.y+upSpeak);
        addT.add(new Text(sp,sp,txt, color,size,timeSpeak));
    }
    public Text hold(String txt){
        Text tt = new Text(pos,pos,txt, color,size);
        addT.add(tt);
        return tt;
    }
    public void del(Text tt){
        delT.add(tt);
    }


    public void setPos(float x,float y){
        pos.x = (int)x;
        pos.y = (int)y;
    }
    public void setPos(Point pos){
        this.pos = new Point(pos);
    }
    public void draw(Canvas canvas){
        if(addT != null)for(Text t: addT)texts.add(t);
        addT= new ArrayList<>();
        if(addMT != null)for(Text t: addMT)movetexts.add(t);
        addMT= new ArrayList<>();
        if(movetexts!=null)for(Text t: movetexts){
                if (t.stayAlive()){
                    t.setPos(pos);
                    t.drawRotate(canvas);
                }
                else delT.add(t);
        }
        if(texts!=null)for(Text t: texts){
            if (t.stayAlive()){
                t.drawRotate(canvas);
            }
            else delT.add(t);
        }
        if(delT!=null)for(Text t: delT){
            texts.remove(t);
            movetexts.remove(t);
        }
        delT= new ArrayList<>();
    }
}
