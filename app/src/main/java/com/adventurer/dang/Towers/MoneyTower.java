package com.adventurer.dang.Towers;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;

import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 6/11/2560.
 */

public class MoneyTower  extends Tower implements AllTower,TileObject {
    private double holdSec=0,ts=Constants.INIT_TIME;
    private float delaySec=10;
    private float goldP=10;

    public MoneyTower(Tile tile,Card card){
        super(tile,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.moneypic),card);
        setStdCB();this.card=card;
        CB_UPGRADE=0;CB_SELL=1;CB_CANCEL=2;
        ts=System.currentTimeMillis();
    }

    @Override
    public void setStat(){
        super.setStat();
        hp=maxHp=DEF*5;
        delaySec = 5-SPD/20;
        if(delaySec<0)delaySec=0;
        goldP = PWR/2;
    }
    @Override
    public void update() {
        super.update();

        if(upSec==0&&holdSec<=0){
            GameScene.MONEY+=goldP;
            balloon.pop((int)goldP+"G", Color.rgb(255,215,0));
            holdSec=delaySec;
        }
        if(ts<Constants.INIT_TIME)ts=Constants.INIT_TIME;
        holdSec-=(System.currentTimeMillis()-ts)/1000;
        ts=System.currentTimeMillis();
    }

}
