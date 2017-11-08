package com.adventurer.dang.Towers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.R;
import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.Tile;

/**
 * Created by x_x on 6/11/2560.
 */

public class Tower extends TextureButton  {
    public static final int NULL=0,MONEY=1,WALL=2,SHOOT=3,SPARK=4;
    protected static int CB_UPGRADE=0,CB_SELL=1,CB_CANCEL=2;
    protected TextureButton CirBut[];
    protected boolean onOpen = false;
    protected Tile tile ;
    protected Card card ;

    protected Tower(Tile tile,Bitmap bitmap){
        super(new Point(tile.getX(),tile.getY()-(Constants.TOWER_HEIGHT-Constants.TILE_SIZE)/2), Constants.TOWER_WIDTH,Constants.TOWER_HEIGHT,bitmap);
        this.tile=tile;
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
                Backpack.addCard(card);
                tile.delTower();
            }
            close();
            return true;
        }
        return false;
    }

    public void drawCB(Canvas canvas) {
        if(onOpen)for(int i = 0;i<CirBut.length;i++)CirBut[i].draw(canvas);
    }

    public void update() {

    }

    public void open(){
        setPointCB();
        onOpen = true;
    }
    public void close(){
        onOpen = false;
    }
}
