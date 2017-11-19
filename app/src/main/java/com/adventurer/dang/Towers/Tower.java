package com.adventurer.dang.Towers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Balloon;
import com.adventurer.dang.Buttons.FullBar;
import com.adventurer.dang.Buttons.MidBar;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileManager;
import com.adventurer.dang.Tiles.TileObject;

/**
 * Created by x_x on 6/11/2560.
 */

public class Tower extends TextureButton implements TileObject {
    public static final int NULL=0,MONEY=1,WALL=2,SHOOT=3,SPARK=4;
    protected static int CB_UPGRADE=0,CB_SELL=1,CB_CANCEL=2;
    protected TextureButton CirBut[];
    protected boolean onOpen = false;
    protected Tile tile ;
    public Card card ;
    protected float hp,maxHp;
    protected FullBar barHp;
    protected Balloon balloon,dataB;
    protected TileManager manager;

    protected MidBar barUp;
    protected double upSec=0,timeStart=Constants.INIT_TIME;
    protected float upTime=10;

    protected int DEF,PWR,SPD,LUCK,LVL=1;

    protected Tower(Tile tile,Bitmap bitmap,Card card){
        super(new Point(tile.getX(),tile.getY()-(Constants.TOWER_HEIGHT-Constants.TILE_SIZE)/2), Constants.TOWER_WIDTH,Constants.TOWER_HEIGHT,bitmap);
        this.tile=tile;
        this.card=card;
        manager = tile.getTileMan();

        timeStart=System.currentTimeMillis();

        barHp=new FullBar(tile.getX(),tile.getY()+Constants.TILE_SIZE/2,(int)(Constants.TILE_SIZE/1.5),Constants.SCREEN_SCALE*15, Color.rgb(255,99,71));
        barUp=new MidBar(tile.getX(),tile.getY(),(int)(Constants.TILE_SIZE/1.8),Constants.SCREEN_SCALE*20, Color.rgb(100,149,237));
        balloon = new Balloon();
        balloon.size=Constants.SCREEN_SCALE*40;
        hp = maxHp = 300;
        DEF=card.getDEF();PWR=card.getPWR();SPD=card.getSPD();LUCK=card.getLUCK();
        setStat();
    }
    protected void setStdCB(){
        CirBut = new TextureButton [] {GameScene.CB_UPGRADE,GameScene.CB_SELL,GameScene.CB_CANCEL};
    }

    public void setPointCB(){
        for(int i = 0;i<CirBut.length;i++){
            CirBut[i].setDestPos((i-(float)(CirBut.length-1)/2)* Constants.CB_SIZE+posX,posY-Constants.TILE_SIZE/2);
            CirBut[i].setStartPos(posX,posY);
        }
    }

    public Boolean clickCB(Point CP){
        for(int b = 0;b<CirBut.length;b++)if(CirBut[b].hitCheck(CP)){
            if(b==CB_SELL){
                die();
                GameScene.MONEY+=30;
            }
            if(b==CB_UPGRADE){
                if(GameScene.MONEY<100)manager.alert.pop("Not Enougt Money");
                else {
                    upSec=upTime;
                    GameScene.MONEY-=100;
                    LVL++;
                }
            }
            close();
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(hp<maxHp){
            barHp.setPos(tile.getX()+Constants.DRAG_DIST.x,tile.getY()+Constants.TILE_SIZE/2+Constants.DRAG_DIST.y);
            barHp.draw(canvas,hp,maxHp);
        }if(upSec>0){
            barUp.setPos(tile.getX()+Constants.DRAG_DIST.x,tile.getY()-Constants.TILE_SIZE/2+Constants.DRAG_DIST.y);
            barUp.draw(canvas,(float) upSec,upTime);
        }
        balloon.setPos(tile.getX()+Constants.DRAG_DIST.x,tile.getY()-Constants.TILE_SIZE/4+Constants.DRAG_DIST.y);
        balloon.draw(canvas);
    }

    public void drawCB(Canvas canvas) {
        if(onOpen){
            for(int i = 0;i<CirBut.length;i++)CirBut[i].draw(canvas);
            if(dataB!=null){
                dataB.setPos(posX+Constants.DRAG_DIST.x,posY-Constants.TILE_SIZE/4+Constants.DRAG_DIST.y);
                dataB.draw(canvas);
            }
        }
    }

    public void update() {
        if(hp>maxHp)hp=maxHp;
        if(hp<=0)die();

        if(upSec>0){
            if(Math.random()*100<LUCK)addStat();
        }

        if(timeStart<Constants.INIT_TIME)timeStart=Constants.INIT_TIME;
        upSec-=(System.currentTimeMillis()-timeStart)/1000;
        if(upSec<0)upSec=0;
        timeStart=System.currentTimeMillis();
    }

    public void addStat(){
        int rand = (int) (Math.random()*100);
        if(rand<32){
            DEF++;
            balloon.pop("DEF",Color.rgb(100,149,237));
        }else if(rand<64){
            PWR++;
            balloon.pop("PWR",Color.rgb(100,149,237));
        }else if(rand<96){
            SPD++;
            balloon.pop("SPD",Color.rgb(100,149,237));
        }else {
            LUCK++;
            balloon.pop("L",Color.rgb(238,130,238));
            balloon.pop("U",Color.rgb(0,191,255));
            balloon.pop("C",Color.rgb	(173,255,47));
            balloon.pop("K",Color.rgb(255,99,71));
        }
        setStat();
    }
    public void setStat(){
        dataB = new Balloon();
        int distLine=Constants.TOWER_HEIGHT/8;
        dataB.size=Constants.TOWER_HEIGHT/7;
        dataB.addMoveText("Level -> "+LVL,distLine);
        dataB.addMoveText("Defend -> "+DEF,distLine*2);
        dataB.addMoveText("Power -> "+PWR,distLine*3);
        dataB.addMoveText("Speed -> "+SPD,distLine*4);
        dataB.addMoveText("Luck ->"+LUCK,distLine*5);
    }

    public void die(){
        tile.delTower();
    }
    public void open(){
        if(upSec>0)return ;
        setPointCB();
        onOpen = true;
    }
    public void close(){
        onOpen = false;
    }

    @Override
    public void pushHp(float value) {
        if(value<0)balloon.pop(""+(int)-value, Color.rgb(255,99,71));
        if(value>0)balloon.pop(""+(int)value,Color.rgb(173,255,47));
        hp+=value;
    }
    public Card getCard(){
        return card;
    }
}
