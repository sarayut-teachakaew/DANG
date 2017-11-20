package com.adventurer.dang.Towers;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 6/11/2560.
 */

public class SparkTower   extends Tower implements AllTower,TileObject {
  public SparkTower(Tile tile,Card card){
        super(tile,new BitmapFactory().decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sparkpic),card);
        setStdCB();this.card=card;
        CB_UPGRADE=0;CB_SELL=1;CB_CANCEL=2;
    }

}
