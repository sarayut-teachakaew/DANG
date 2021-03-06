package com.adventurer.dang;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.adventurer.dang.Boukenshas.Boukensha;
import com.adventurer.dang.Boukenshas.Player;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileManager;
import com.adventurer.dang.Tiles.TileObject;
import com.adventurer.dang.Towers.Tower;

/**
 * Created by x_x on 16/11/2560.
 */

public class Bullet implements TileObject {
    public static int SCALE_SIZE=20,DAMAGE_ALL=0,DAMAGE_ENEMY=1,PLAYER_BULLET=2,DAMAGE_FRIENDLY=-1;

    private TileManager manager;
    public int type=2,size,power;
    public Point pos,firePos,speed;
    public Paint paint;TileObject notThis;

    public Bullet(Point pos, Point speed, int size, int power, int type, TileManager manager){
        this.pos=pos;this.speed=speed;this.size=size;firePos=new Point (pos);
        this.type=type;this.power=power;this.manager=manager;
        paint=new Paint();

        if(type==PLAYER_BULLET){
            paint.setColor(Color.rgb(100,149,237));
        }else if(type==DAMAGE_ALL){
            paint.setColor(Color.rgb(238,130,238));
        }else if(type==DAMAGE_ENEMY){
            paint.setColor(Color.rgb	(240,230,140));
        }else if(type==DAMAGE_FRIENDLY){
            paint.setColor(Color.rgb(255,165,0));
        }
    }
    public Bullet(Point pos, float rot,float spd, int size, int power, int type, TileManager manager){
        this(pos,new Point((int)(spd*Math.cos(Math.toRadians(rot))),(int)(spd*Math.sin(Math.toRadians(rot)))),size,power,type,manager);
    }
    public Bullet(Point pos, float rot,float spd, int size, int power, int type, TileManager manager,TileObject notThis){
        this(pos,new Point((int)(spd*Math.cos(Math.toRadians(rot))),(int)(spd*Math.sin(Math.toRadians(rot)))),size,power,type,manager);
        this.notThis=notThis;
    }
    public void update(){
        pos.x+=speed.x*Constants.SCREEN_SCALE;
        pos.y+=speed.y*Constants.SCREEN_SCALE;

        if(pos.x<0||pos.x>manager.getWidth()||pos.y<0||pos.y>manager.getHeight())manager.delBullet(this);

        TileObject TO = manager.visionCheck(pos.x,pos.y,notThis);
        if(TO !=null){
        if (TO instanceof Tile) {
            if(!(((Tile) TO).isWalkable())&&TO!=notThis)manager.delBullet(this);}
        else if (TO instanceof Tower) {
            if(type == PLAYER_BULLET){
                TO.pushHp(power/4);
                manager.delBullet(this);
            }
            else if(TO!=notThis)manager.delBullet(this);
        }
        else if(TO instanceof Boukensha) {
            if(type==DAMAGE_ALL||(((Boukensha)TO).isFriendly()&&type==DAMAGE_FRIENDLY)||(!((Boukensha)TO).isFriendly()&&(type==DAMAGE_ENEMY||type==PLAYER_BULLET))){
                TO.pushHp(-power);
                manager.delBullet(this);
            }else if(!((Boukensha)TO).isFriendly()&&(type==DAMAGE_ENEMY||type==PLAYER_BULLET)){
                ((Boukensha)TO).getAttention(firePos);
            }
        }
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x+Constants.DRAG_DIST.x
                ,pos.y+Constants.DRAG_DIST.y -Player.STEP_UP
                ,SCALE_SIZE*size/2,paint);
    }
    public Point getPos(){
        return pos;
    }

    @Override
    public void pushHp(float hp) {

    }
}
