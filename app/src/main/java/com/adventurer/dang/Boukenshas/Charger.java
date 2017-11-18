package com.adventurer.dang.Boukenshas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Buttons.MidBar;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileManager;
import com.adventurer.dang.Tiles.TileObject;
import com.adventurer.dang.Towers.AllTower;
import com.adventurer.dang.Towers.MoneyTower;
import com.adventurer.dang.Towers.ShootTower;
import com.adventurer.dang.Towers.SparkTower;
import com.adventurer.dang.Towers.WallTower;

import java.util.ArrayList;

/**
 * Created by x_x on 17/11/2560.
 */

public class Charger extends SimpleBoukensha implements Boukensha {
    private static int PLAYER_PRRT=10,MONNEY_TOWER_PRRT=2,SPARK_TOWER_PRRT=4,SHOOT_TOWER_PRRT=4,WALL_TOWER_PRRT=1;
    private Point targetP;
    private Boolean FRU;
    private int cC=0;
    private int vision=90,visDist=Constants.SCREEN_SCALE*1000;
    private boolean onCharge=false;
    private double holdSec=0,timeStart=Constants.INIT_TIME;
    private float chargeSec = 0.7f;
    private MidBar chargeBar;

    public Charger(TileManager manager, int x, int y ){
        super();
        this.x=x;this.y=y;this.manager = manager;power=5;
        texture = GameScene.pic.monster_charger;
        textureAimor0 = GameScene.pic.aimor2_low;
        textureAimor1 = GameScene.pic.aimor2_high;
        action=ON_AIM;

        timeStart=System.currentTimeMillis();
        moveSPD=Constants.SCREEN_SCALE*10;
        hp=maxHp = 50;
        power=1;
        chargeBar = new MidBar(x,y-STEP_UP,Constants.SCREEN_SCALE*100,Constants.SCREEN_SCALE*30, Color.rgb(255,105,180));

        newTP();
    }

    public void survey(Canvas canvas){
        TileObject tarO=manager.player;
        int maxPrrt=0;
        boolean CT=false;
        for(int rad=(int)aimRot-vision/2;rad<=aimRot+vision/2;rad+=10) {
            for (float dist = 0; dist < visDist; dist += Player.WIDTH/4) {
                Paint pn = new Paint();pn.setColor(Color.RED);
                Point pp = new Point((int) (x+Math.cos(Math.toRadians(rad)) * dist), (int) (y+Math.sin(Math.toRadians(rad)) * dist));
                canvas.drawRect(new Rect(pp.x-5+Constants.DRAG_DIST.x,pp.y-5+Constants.DRAG_DIST.y
                        ,pp.x+5+Constants.DRAG_DIST.x,pp.y+5+Constants.DRAG_DIST.y),pn);
                TileObject TO = manager.visionCheck(x+(float) Math.cos(Math.toRadians(rad)) * dist, y+(float) Math.sin(Math.toRadians(rad)) * dist,this);
                if (TO == null) continue;
                else if (TO instanceof Tile) {
                    if (((Tile) TO).isWalkable()) continue;
                    else break;
                } else if (TO instanceof Boukensha) {
                    if (TO == manager.player) {
                        if(maxPrrt<PLAYER_PRRT)
                        {
                            tarO=manager.player;
                            maxPrrt = PLAYER_PRRT;
                        }
                        break;
                    } else {
                        break;
                    }
                } else if (TO instanceof AllTower) {
                    if(TO instanceof ShootTower && maxPrrt<SHOOT_TOWER_PRRT){
                        tarO=TO;
                        maxPrrt = SHOOT_TOWER_PRRT;
                        CT=true;
                    }else if(TO instanceof SparkTower && maxPrrt<SPARK_TOWER_PRRT){
                        tarO=TO;
                        maxPrrt = SPARK_TOWER_PRRT;
                        CT=true;
                    }else if(TO instanceof MoneyTower && maxPrrt<MONNEY_TOWER_PRRT){
                        tarO=TO;
                        maxPrrt = MONNEY_TOWER_PRRT;
                        CT=true;
                    }else if(TO instanceof WallTower && maxPrrt<WALL_TOWER_PRRT){
                        tarO=TO;
                        maxPrrt = WALL_TOWER_PRRT;
                        CT=true;
                    }
                    break;
                }
            }
        }
        if(maxPrrt >0){
            targetP = new Point(tarO.getPos());
            if(CT&&!onCharge&&Math.random()>0.96){
                charge();
            }
        }
    }
    public void newTP(){
        ArrayList<Tile> ranT;
        if(moveStuck>100)ranT = manager.rangeTile(x,y,3);
        else ranT = manager.rangeTile((float) (x+Constants.SCREEN_SCALE*Constants.SCREEN_SCALE*300*Math.cos(Math.toRadians(aimRot)))
                ,(float)(y+Constants.SCREEN_SCALE*Constants.SCREEN_SCALE*300*Math.sin(Math.toRadians(aimRot))),1);
        if(ranT.size()==0)return;
        targetP= ranT.get((int)(Math.random()*ranT.size())%ranT.size()).getPos();
        if(targetRot()>aimRot)FRU=true;
        else FRU=false;
    }
    public float targetRot(){
        return (float) Math.toDegrees(Math.atan2(targetP.y-y, targetP.x-x));
    }

    @Override
    public void draw(Canvas canvas) {
        float dis=(float) Math.sqrt((manager.player.getX()-x)*(manager.player.getX()-x)+(manager.player.getY()-y)*(manager.player.getY()-y));
        if(dis>Constants.SCREEN_SCALE*2400)return;

        super.draw(canvas);
        canvas.drawRect(new Rect(targetP.x-20+Constants.DRAG_DIST.x,targetP.y-20+Constants.DRAG_DIST.y
                ,targetP.x+20+Constants.DRAG_DIST.x,targetP.y+20+Constants.DRAG_DIST.y),new Paint());
        if(onCharge){
            chargeBar.setPos(x+Constants.DRAG_DIST.x,y-STEP_UP+Constants.DRAG_DIST.y);
            chargeBar.draw(canvas,(float) holdSec,chargeSec);
        }
        survey(canvas);
    }

    @Override
    public void update() {
        if(hp>maxHp)hp=maxHp;
        if(hp<=0) die();

        float dis=(float) Math.sqrt((manager.player.getX()-x)*(manager.player.getX()-x)+(manager.player.getY()-y)*(manager.player.getY()-y));
        if(dis>Constants.SCREEN_SCALE*2000)return;
        if(timeStart<Constants.INIT_TIME)timeStart=Constants.INIT_TIME;
        holdSec-=(System.currentTimeMillis()-timeStart)/1000;
        timeStart=System.currentTimeMillis();
        if(holdSec>0)return;

        super.update();

        if(Math.abs(ax)+Math.abs(ay)<1&&onCharge)onCharge=false;

        if(onCharge){
            if(dis<Constants.SCREEN_SCALE*100){
                manager.player.pushHp(-power*10);
                balloon.pop("บึก!!",Color.rgb(0,0,0));
            }
            AllTower tower = manager.towerCheck((float) (x+Constants.SCREEN_SCALE*20*Math.cos(Math.toRadians(aimRot)))
                    ,(float)(y+Constants.SCREEN_SCALE*20*Math.sin(Math.toRadians(aimRot))));
            if(tower!=null){
                tower.pushHp(-power*3);
                balloon.pop("ปึง",Color.rgb(0,0,0));
                ax/=2;ay/=2;
            }
        }

        if(moveStuck>150){
            newTP();
            moveStuck=0;
        }
        if(onCharge) action=ON_ATTACK;
        else action=ON_AIM;
        if(action==ON_AIM){
            if (targetP!=null) {
                 if(Math.abs(targetRot()-aimRot)>10){
                 if(FRU){
                        while (targetRot()<aimRot)aimRot-=360;
                     aimRot+=(targetRot()-aimRot)/4;
                    }
                    else{
                        while (targetRot()>aimRot)aimRot+=360;
                        aimRot-=(aimRot-targetRot())/4;
                    }
                }else {
                     aimRot=targetRot();
                 }
                double dist = Math.sqrt((targetP.x-x)*(targetP.x-x)+(targetP.y-y)*(targetP.y-y));
                if(dist<=moveSPD) {
                    newTP();
                }else {
                    moveRot(aimRot,moveSPD);
                }
            }else {
                newTP();
            }
            double d = Math.sqrt((manager.player.getX()-targetP.x)*(manager.player.getX()-targetP.x)
                    +(manager.player.getY()-targetP.y)*(manager.player.getY()-targetP.y));
            if(d<Constants.SCREEN_SCALE*50)cC++;
            else if(cC>0)cC--;
            if(dis<Constants.SCREEN_SCALE*500)cC+=5;
            if(cC>100&&Math.random()>0.8){
                charge();
            }
        }
    }
    public void charge(){
        holdSec =chargeSec;
        float force = 80;
        pushForce((float) (Constants.SCREEN_SCALE*force*Math.cos(Math.toRadians(aimRot)))
                ,(float)(Constants.SCREEN_SCALE*force*Math.sin(Math.toRadians(aimRot))));
        onCharge=true;
        cC=0;
    }
}
