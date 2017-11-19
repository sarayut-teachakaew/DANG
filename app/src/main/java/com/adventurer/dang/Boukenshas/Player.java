package com.adventurer.dang.Boukenshas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.adventurer.dang.Bullet;
import com.adventurer.dang.Buttons.MidBar;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Controller;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.TileManager;

/**
 * Created by x_x on 15/11/2560.
 */

public class Player extends SimpleBoukensha implements Boukensha {
    public float bulletSpd=20;
    public float shake=0,maxShake=40;
    public double secPerFire=0.2;
    public double fireCD=0,timeStart=Constants.INIT_TIME;
    public MidBar barShake,barCD;

    public Player(TileManager manager,int x, int y ){
        super();
        this.x=x;this.y=y;this.manager = manager;power=5;
        texture = GameScene.pic.player;
        textureAimor0 = GameScene.pic.aimor1_low;
        textureAimor1 = GameScene.pic.aimor1_high;

        int barWidth=Controller.SIZE,barHeight=Controller.SIZE/24;
        barCD=new MidBar(Constants.SCREEN_WIDTH-Controller.SIZE*2/3,Constants.SCREEN_HEIGHT-Controller.SIZE*7/6-barHeight
                , barWidth,barHeight,(float) fireCD,(float) secPerFire, Color.rgb(176,224,230));
        barShake=new MidBar(Constants.SCREEN_WIDTH-Controller.SIZE*2/3,Constants.SCREEN_HEIGHT-Controller.SIZE*7/6-barHeight-barHeight*2
                , barWidth,barHeight,(float) shake,(float) maxShake, Color.rgb(233,150,122));

        bulletSpd = Constants.SCREEN_SCALE*60;
        hp=maxHp = 100;
    }
    @Override
    public void update() {
        if(timeStart<Constants.INIT_TIME)timeStart=Constants.INIT_TIME;
        if(action==ON_FIRE){
            if(fireCD<=0)fireBullet();
        }
        if(shake>maxShake)shake=maxShake;
        if(action!=ON_FIRE)if(shake>0)shake-=2;
        else if(shake>0)shake-=0.1;
        if(shake<0)shake=0;
        fireCD-=(System.currentTimeMillis()-timeStart)/1000;
        timeStart=System.currentTimeMillis();
        moveStuck=0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        barCD.draw(canvas,(float) fireCD,(float) secPerFire);
        barShake.draw(canvas,shake,maxShake);
    }

    void fireBullet(){
        aimRot=aimRot-shake/2+(float) (Math.random()*shake);
        manager.addBullet(new Bullet(new Point(x+(int)(AIMOR_SIZE/2*Math.cos(Math.toRadians(aimRot))),y+(int)(AIMOR_SIZE/2*Math.sin(Math.toRadians(aimRot))))
                ,aimRot,bulletSpd,2,(int)power,Bullet.PLAYER_BULLET,manager));
        shake+=2;
        fireCD = secPerFire;
    }

    public Point getPos(){
        return new Point(x,y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isFriendly(){
        return true;
    }
}
