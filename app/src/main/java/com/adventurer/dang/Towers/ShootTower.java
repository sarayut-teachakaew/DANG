package com.adventurer.dang.Towers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Boukenshas.Boukensha;
import com.adventurer.dang.Boukenshas.Player;
import com.adventurer.dang.Bullet;
import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileObject;


/**
 * Created by x_x on 6/11/2560.
 */

public class ShootTower   extends Tower implements AllTower,TileObject {
    private double holdSec=0,ts=Constants.INIT_TIME;int swC=0,swCM=30;
    private float delaySec=2,damage=1,rng=1000,Bspd=Constants.SCREEN_SCALE*30;

    public ShootTower(Tile tile,Card card){
        super(tile,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tower_shoot),card);
        setStdCB();this.card=card;
        CB_UPGRADE=0;CB_SELL=1;CB_CANCEL=2;

        setStat();
    }
    public Point surveyP(Canvas canvas){
        if(swC < swCM)return null;
        Point tp=null;double mindis = Constants.SCREEN_SCALE*1401;
            for(int rad=0;rad<=360;rad+=10) {
                for (float dist = -1; dist < mindis; dist += Player.WIDTH / 2) {
                    /*Paint pn = new Paint();
                    pn.setColor(Color.BLUE);
                    Point pp = new Point((int) (getX() + Math.cos(Math.toRadians(rad)) * dist), (int) (getY() + Math.sin(Math.toRadians(rad)) * dist));
                    canvas.drawRect(new Rect(pp.x - 5 + Constants.DRAG_DIST.x, pp.y - 5 + Constants.DRAG_DIST.y
                            , pp.x + 5 + Constants.DRAG_DIST.x, pp.y + 5 + Constants.DRAG_DIST.y), pn);*/

                    TileObject TO = manager.visionCheck(getX() + (float) Math.cos(Math.toRadians(rad)) * dist, getY() + (float) Math.sin(Math.toRadians(rad)) * dist, this);
                    if (TO == null) continue;
                    else if (TO instanceof Tile) {
                        if (((Tile) TO).isWalkable()) continue;
                        else break;
                    } else if (TO instanceof Boukensha && !(((Boukensha) TO).isFriendly())) {
                        tp=new Point(TO.getPos());
                        mindis = Math.sqrt((getX()-((Boukensha) TO).getX())*(getX()-((Boukensha) TO).getX())+(getY()-((Boukensha) TO).getY())*(getY()-((Boukensha) TO).getY()));
                    }
                }
            }
        swC=0;
        return tp;
    }
    @Override
    public void setStat(){
        super.setStat();
        hp=maxHp=DEF*10;
        delaySec = 2-SPD/50;
        if(delaySec<0)delaySec=0;
        damage = PWR;
    }
    @Override
    public void update() {
        super.update();

        if(swC < swCM)swC++;

        if(ts<Constants.INIT_TIME)ts=Constants.INIT_TIME;
        holdSec-=(System.currentTimeMillis()-ts)/1000;
        ts=System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(holdSec<=0){
            Point tp = surveyP(canvas);
            if(tp!=null){
                manager.addBullet(new Bullet(getPos(),(float)Math.toDegrees(Math.atan2(tp.y-getY(), tp.x-getX())),Bspd,3,(int)damage, Bullet.DAMAGE_ENEMY,manager,this));
                holdSec=delaySec;
            }
        }
    }
}
