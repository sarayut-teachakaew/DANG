package com.adventurer.dang;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Scenes.SceneManager;

/**
 * Created by x_x on 6/11/2560.
 */

public class Card extends TextureButton {
    public static final int NULL=0,MONEY_TOWER=1,WALL_TOWER=2,SHOOT_TOWER=3,SPARK_TOWER=4;
    private int DEF,PWR,SPD,LUCK,rare=0;
    private Balloon balloon;
    public static Bitmap[] texture = new Bitmap[]
            {SceneManager.pic.card_null
            ,SceneManager.pic.tcard_money
            ,SceneManager.pic.tcard_wall
            ,SceneManager.pic.tcard_shoot
            ,SceneManager.pic.tcard_spark};

    private static int[] cardChance=new int[]{
            MONEY_TOWER,WALL_TOWER,SHOOT_TOWER,SPARK_TOWER
    };

    private int type=0;

    public Card(int i){
        super(new Point(0,0),Constants.CARD_WIDTH,Constants.CARD_HEIGHT,texture[(texture.length>i&&i>=0)? i:0]);
        type = (texture.length>i&&i>=0)? i:0;
        panPic = false;

        DEF = 10;PWR = 10;SPD = 10;LUCK=1;
        do{
            DEF+=3+(int)(Math.random()*10);
            PWR+=3+(int)(Math.random()*10);
            SPD+=3+(int)(Math.random()*10);
            LUCK+=(int)(Math.random()*2)+(int)(Math.random()*2);
            rare++;
        } while (Math.random()>0.6);
        /*if(DEF>=100)DEF=100;
        if(PWR>=100)PWR=100;
        if(SPD>=100)SPD=100;
        if(LUCK>=100)LUCK=100;*/
        int distLine=Constants.CARD_HEIGHT/12;
        balloon = new Balloon();
        balloon.setPos(posX,posY);
        balloon.size=Constants.CARD_HEIGHT/11;
        balloon.addMoveText("Rarity -> "+rare,distLine);
        balloon.addMoveText("Defend -> "+DEF,distLine*2);
        balloon.addMoveText("Power -> "+PWR,distLine*3);
        balloon.addMoveText("Speed -> "+SPD,distLine*4);
        balloon.addMoveText("Luck ->"+LUCK,distLine*5);

    }
    public Card(){
        this(cardChance[(int)(Math.random()*cardChance.length)%cardChance.length]);
    }
    public int getType(){return type;}

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        balloon.setPos(posX,posY);
        balloon.draw(canvas);
    }
    public int getRarity(){
        return rare;
    }
    public int getDEF(){
        return DEF;
    }
    public int getPWR(){
        return PWR;
    }
    public int getSPD(){
        return SPD;
    }
    public int getLUCK(){
        return LUCK;
    }
}
