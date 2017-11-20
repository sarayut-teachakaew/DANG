package com.adventurer.dang.Boukenshas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Balloon;
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

import static com.adventurer.dang.Backpack.check_score;
import static com.adventurer.dang.Backpack.score;

/**
 * Created by x_x on 17/11/2560.
 */

public class Charger extends SimpleBoukensha implements Boukensha {
    private static int PLAYER_PRRT=10, ATTENTION_PRRT =9,MONNEY_TOWER_PRRT=2,SPARK_TOWER_PRRT=4,SHOOT_TOWER_PRRT=4,WALL_TOWER_PRRT=1;
    private Point targetP;
    private Boolean FRU;
    private int maxPrrt=0;
    private int cC=0,swC=0,swCM=30;
    private int vision=90,visDist=(int)(Constants.SCREEN_SCALE*1000);
    private boolean onCharge=false,meetYet=false;
    private double holdSec=0,timeStart=Constants.INIT_TIME;
    private float chargeSec = 0.7f;
    private MidBar chargeBar;
    private Balloon balli;

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
        chargeBar = new MidBar(x,y-STEP_UP,(int)(Constants.SCREEN_SCALE*100),(int)(Constants.SCREEN_SCALE*30), Color.rgb(255,105,180));

        newTP();
    }

    public void survey(Canvas canvas){
        if(swC<swCM)return;
        TileObject tarO=manager.player;
        boolean CT=false;
        for(int rad=(int)aimRot-vision/2;rad<=aimRot+vision/2;rad+=10) {
            for (float dist = -1; dist < visDist;dist += (dist<Player.WIDTH/10)? 1:Player.WIDTH/2-1) {
                Paint pn = new Paint();
                pn.setColor(Color.RED);
                Point pp = new Point((int) (x + Math.cos(Math.toRadians(rad)) * dist), (int) (y + Math.sin(Math.toRadians(rad)) * dist));
                canvas.drawRect(new Rect(pp.x - 5 + Constants.DRAG_DIST.x, pp.y - 5 + Constants.DRAG_DIST.y
                        , pp.x + 5 + Constants.DRAG_DIST.x, pp.y + 5 + Constants.DRAG_DIST.y), pn);

                TileObject TO = manager.visionCheck(x + (float) Math.cos(Math.toRadians(rad)) * dist, y + (float) Math.sin(Math.toRadians(rad)) * dist, this);
                if (TO == null) continue;
                else if (TO instanceof Tile) {
                    if (((Tile) TO).isWalkable()) continue;
                    else break;
                } else if (TO instanceof Boukensha) {
                    if (TO == manager.player) {
                        if (maxPrrt < PLAYER_PRRT) {
                            tarO = manager.player;
                            maxPrrt = PLAYER_PRRT;
                        }
                        break;
                    } else {
                        break;
                    }
                } else if (TO instanceof AllTower) {
                    if (TO instanceof ShootTower && maxPrrt < SHOOT_TOWER_PRRT) {
                        tarO = TO;
                        maxPrrt = SHOOT_TOWER_PRRT;
                        CT = true;
                    } else if (TO instanceof SparkTower && maxPrrt < SPARK_TOWER_PRRT) {
                        tarO = TO;
                        maxPrrt = SPARK_TOWER_PRRT;
                        CT = true;
                    } else if (TO instanceof MoneyTower && maxPrrt < MONNEY_TOWER_PRRT) {
                        tarO = TO;
                        maxPrrt = MONNEY_TOWER_PRRT;
                        CT = true;
                    } else if (TO instanceof WallTower && maxPrrt < WALL_TOWER_PRRT) {
                        //System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                        tarO = TO;
                        maxPrrt = WALL_TOWER_PRRT;
                        CT = true;
                    }
                    break;
                }


            }
        }
        if(maxPrrt >0){
            meetYet=true;
            setTP(new Point(tarO.getPos()));
            if(CT&&!onCharge&&Math.abs(targetRot()-aimRot)<10){
                charge();
            }
        }swC=0;
    }
    public void setTP(Point np){
        targetP = new Point(np);
        if(targetRot()>aimRot)FRU=true;
        else FRU=false;
    }
    public void newTP(){
        try{
        ArrayList<Tile> ranT;
        if(moveStuck>90)ranT = manager.rangeTile(x,y,2);
        else if(!meetYet&&Math.random()>0.4)ranT = manager.rangeTile(manager.getWidth()/2,manager.getHeight()/2,7);
        else ranT = manager.rangeTile((float) (x+Constants.SCREEN_SCALE*Constants.SCREEN_SCALE*300*Math.cos(Math.toRadians(aimRot)))
                ,(float)(y+Constants.SCREEN_SCALE*Constants.SCREEN_SCALE*300*Math.sin(Math.toRadians(aimRot))),2);

        if(ranT.size()==0)ranT = manager.rangeTile(x,y,3);if(ranT.size()==0)return;
        targetP= ranT.get((int)(Math.random()*ranT.size())%ranT.size()).getPos();
        maxPrrt=0;
        if(targetRot()>aimRot)FRU=true;
        else FRU=false;}catch (Exception e) {System.out.println(e);}
    }
    public float targetRot(){
        return (float) Math.toDegrees(Math.atan2(targetP.y-y, targetP.x-x));
    }
    @Override
    public void draw(Canvas canvas) {
        if(!manager.isClose(x,y))return;

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

        swC++;

        float dis=(float) Math.sqrt((manager.player.getX()-x)*(manager.player.getX()-x)+(manager.player.getY()-y)*(manager.player.getY()-y));
        if(timeStart<Constants.INIT_TIME)timeStart=Constants.INIT_TIME;
        holdSec-=(System.currentTimeMillis()-timeStart)/1000;
        timeStart=System.currentTimeMillis();
        if(holdSec>0)return;
        if(action==ON_IDLE)action=ON_AIM;
        super.update();

        if(Math.abs(ax)+Math.abs(ay)<4&&onCharge)onCharge=false;

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
            }
        }

        if(moveStuck>100){
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
                    }swC+=5;
                }else {
                     aimRot=targetRot();
                 }
                double dist = Math.sqrt((targetP.x-x)*(targetP.x-x)+(targetP.y-y)*(targetP.y-y));
                if(dist<=moveSPD&&maxPrrt==0) {
                    newTP();
                }else {
                    moveRot(aimRot,moveSPD);
                }
            }else {
                newTP();
            }
            /*if(Math.abs(ax)+Math.abs(ay)>0.00001&&moveStuck!=0){
                System.out.println("=======================0==="+moveStuck);
            }*/
            if(!manager.isClose(x,y)|| targetP==null)return;
            double d = Math.sqrt((manager.player.getX()-targetP.x)*(manager.player.getX()-targetP.x)
                    +(manager.player.getY()-targetP.y)*(manager.player.getY()-targetP.y));
            if(d<Constants.SCREEN_SCALE*50){
                cC++;
                if(dis<Constants.SCREEN_SCALE*400)cC+=3;
            }
            else if(cC>0)cC--;
            if(cC>40&&Math.random()>0.82){
                charge();
            }
        }
        if(onCharge&&moveStuck>1){
            holdSec =chargeSec/2;
            onCharge=false;
            action=ON_IDLE;
            moveStuck=0;
        }
    }
    public void charge(){
        moveStuck=0;
        holdSec =chargeSec;
        float force = 80;
        pushForce((float) (Constants.SCREEN_SCALE*force*Math.cos(Math.toRadians(aimRot)))
                ,(float)(Constants.SCREEN_SCALE*force*Math.sin(Math.toRadians(aimRot))));
        onCharge=true;
        cC=0;
    }

    @Override
    public void getAttention(Point ap) {
        super.getAttention(ap);
        if(maxPrrt< ATTENTION_PRRT){
            maxPrrt=ATTENTION_PRRT;
            setTP(ap);
        }
    }

    @Override
    public void die() {
        super.die();
        GameScene.MONEY+=30;
        check_score++;

        if(check_score > score){
            score = check_score;
            Backpack.savescore();

        }

//        if(Math.random()>0.9){
            manager.alert.pop("Get Coin !!!",Color.rgb(0,0,0));
            Backpack.COIN++;
            Backpack.saveCoin();
//        }

    }

}
