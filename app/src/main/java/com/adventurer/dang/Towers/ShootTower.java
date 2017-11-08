package com.adventurer.dang.Towers;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Tiles.Tile;

/**
 * Created by x_x on 6/11/2560.
 */

public class ShootTower   extends Tower implements AllTower  {

    public ShootTower(Tile tile,Card card){
        super(tile,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tower_shoot));
        setStdCB();this.card=card;
        CB_UPGRADE=0;CB_SELL=1;CB_CANCEL=2;
    }

}
