package com.adventurer.dang.Towers;

import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 6/11/2560.
 */

public class WallTower   extends Tower implements AllTower,TileObject {
    private double holdSec=0,ts=Constants.INIT_TIME;
    private float delaySec=10;
    private float healP=10;

    public WallTower(Tile tile,Card card){
        super(tile,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wallpic),card);
        setStdCB();this.card=card;
        CB_UPGRADE=0;CB_SELL=1;CB_CANCEL=2;
        ts=System.currentTimeMillis();
    }
    @Override
    public void setStat(){
        super.setStat();
        hp=maxHp=DEF*10+PWR*5;
        delaySec = 10-PWR/10;
        if(delaySec<0)delaySec=0;
        healP = SPD;
    }
    @Override
    public void update() {
        super.update();

        if(upSec==0&&holdSec<=0&&hp<maxHp){
            pushHp(healP);
            holdSec=1;
        }
        if(ts<Constants.INIT_TIME)ts=Constants.INIT_TIME;
        holdSec-=(System.currentTimeMillis()-ts)/1000;
        ts=System.currentTimeMillis();
    }
    @Override
    public void pushHp(float value) {
        if(value<0){
            balloon.pop(""+(int)-value, Color.rgb(255,99,71));
            holdSec=delaySec;
        }
        if(value>0)balloon.pop(""+(int)value,Color.rgb(173,255,47));
        hp+=value;
    }
}
