package com.adventurer.dang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.adventurer.dang.Scenes.SceneManager;

/**
 * Created by x_x on 17/11/2560.
 */

public class Pic {
    public Bitmap aimor1_high,aimor1_low,aimor2_high,aimor2_low,card_null
            ,cb_build,cb_buy,cb_cancel,cb_sell,cb_upgrade,cb_warp,controller_button,controller_fire
            ,controller_fire_unactive,controller_move,controller_move_unactive,monster_bommer
            ,monster_charger,monster_gunner,player,spawn_tile,tcard_money,tcard_shoot,tcard_spark
            ,tcard_wall,tower_money,tower_shoot,tower_spark,tower_wall,unseen_tile,coin_pic
            ,floor01,floor02,floor03,floor04
            ,money,moneypic,rock,rock2,rock3,shoot,sparkpic,spark,spawnpic,spawn,towerpic,tower,wallpic,wall;
    public Bitmap floor[],rock0[];


    public Pic(int scene){
        BitmapFactory bf = new BitmapFactory();
        if(scene== SceneManager.GAME_SCENE){

            aimor1_high = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aimor1_high);
            aimor1_low =bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aimor1_low);
            aimor2_high=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aimor2_high);
            aimor2_low=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aimor2_low);

            spawn_tile=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spawn);

            unseen_tile=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.unseen_tile);

            cb_build=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_build);
            cb_buy=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_buy);
            cb_cancel=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_cancel);
            cb_sell=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_sell);
            cb_upgrade=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_upgrade);
            cb_warp=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cb_warp);

            controller_button=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.controller_button);
            controller_fire=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.controller_fire);
            controller_fire_unactive=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.controller_fire_unactive);
            controller_move=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.controller_move);
            controller_move_unactive=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.controller_move_unactive);

            monster_bommer=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.monster_bommer);
            monster_charger=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.monster_charger);
            monster_gunner=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.monster_gunner);

            player=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player);

            floor01=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.floor01);
            floor02=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.floor02);
            floor03=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.floor03);
            floor04=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.floor04);

            floor = new Bitmap[]{floor01,floor02,floor03,floor04};

            rock=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.rock);
            rock2=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.rock2);
            rock3=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.rock3);

            rock0 = new Bitmap[]{rock,rock2,rock3};

//            tower_money=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.moneypic);
//            tower_spark=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sparkpic);
//            tower_shoot=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.towerpic);
//            tower_wall=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wallpic);


        }

        card_null=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.card_null);

        tcard_money=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.money);
        tcard_shoot=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shoot);
        tcard_spark=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spark);
        tcard_wall=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wall);

        coin_pic=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin_pic);
    }
    public Bitmap randomNormalTile(){
        return floor[(int)(Math.random()*floor.length)%floor.length];
    }
    public Bitmap randomBoulderTile(){
        return rock0[(int)(Math.random()*rock0.length)%rock0.length];
    }

}
