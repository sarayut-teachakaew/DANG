package com.adventurer.dang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.adventurer.dang.Buttons.TextureButton;

/**
 * Created by x_x on 6/11/2560.
 */

public class Card extends TextureButton {
    public static final int NULL=0,MONEY_TOWER=1,WALL_TOWER=2,SHOOT_TOWER=3,SPARK_TOWER=4;
    private static BitmapFactory bf = new BitmapFactory();
    public static Bitmap[] texture = new Bitmap[]
            {bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.card_null)
            ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tcard_money)
            ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tcard_wall)
            ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tcard_shoot)
            ,bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tcard_spark)};

    private int type=0;

    public Card(Point pos,int i){
        super(pos,Constants.CARD_WIDTH,Constants.CARD_HEIGHT,texture[(texture.length>i&&i>=0)? i:0]);
        type = (texture.length>i&&i>=0)? i:0;
        panPic = false;
    }
    public int getType(){return type;}
}
