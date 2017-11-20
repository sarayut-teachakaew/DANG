package com.adventurer.dang.Boukenshas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Balloon;
import com.adventurer.dang.Buttons.FullBar;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Tiles.TileManager;

import static com.adventurer.dang.Backpack.check_score;
import static com.adventurer.dang.Backpack.score;

/**
 * Created by x_x on 17/11/2560.
 */

public class SimpleBoukensha implements Boukensha{
    public static int ON_IDLE=0,ON_AIM=1,ON_ATTACK=2,ON_FIRE=2;
    public static int WIDTH = 100,HEIGHT=150;
    public static int AIMOR_SIZE=100;
    public static int STEP_UP;

    protected Bitmap texture, textureAimor0, textureAimor1;
    protected float aimRot=0;
    protected float moveSPD=Constants.SCREEN_SCALE*14;
    protected TileManager manager;
    protected float hp,maxHp;
    protected FullBar barHp;
    protected int x,y;
    protected float ax=0,ay=0;
    protected Balloon balloon;
    protected int moveStuck=0;

    public int action=0;
    public float power=5;

    protected SimpleBoukensha(){
        int barWidth= WIDTH,barHeight=HEIGHT/24;
        barHp=new FullBar(x,y,barWidth,barHeight, Color.rgb(255,99,71));
        balloon = new Balloon();
        balloon.size=barHeight*4;
    }
    @Override
    public void draw(Canvas canvas) {
        Point posPoint = new Point(x+Constants.DRAG_DIST.x
                ,y+Constants.DRAG_DIST.y);

        canvas.drawBitmap(texture,null,new Rect(posPoint.x-WIDTH/2,posPoint.y-HEIGHT
                ,posPoint.x+WIDTH/2,posPoint.y),new Paint());

        if(hp<maxHp){
            barHp.setPos(posPoint);
            barHp.draw(canvas,hp,maxHp);
        }
        if(action>ON_IDLE){
            canvas.save();
            canvas.rotate(aimRot+90,posPoint.x,posPoint.y-STEP_UP);
            if(action==ON_AIM)canvas.drawBitmap(textureAimor0,null,new Rect(posPoint.x-AIMOR_SIZE/2,posPoint.y-AIMOR_SIZE-STEP_UP
                    ,posPoint.x+AIMOR_SIZE/2,posPoint.y-STEP_UP),new Paint());
            else canvas.drawBitmap(textureAimor1,null,new Rect(posPoint.x-AIMOR_SIZE/2,posPoint.y-AIMOR_SIZE-STEP_UP
                    ,posPoint.x+AIMOR_SIZE/2,posPoint.y-STEP_UP),new Paint());
            canvas.restore();
        }
        balloon.setPos(posPoint.x,posPoint.y-STEP_UP);
        balloon.draw(canvas);
    }

    @Override
    public void update() {
        move(ax,ay);
        ax*=0.95;
        ay*=0.95;
    }

    @Override
    public void die() {
        manager.delBoukensha(this);

    }

    @Override
    public void pushForce(float x, float y) {
        ax+=x;ay+=y;
    }

    @Override
    public void move(float mx, float my) {
        try {
            mx *= Constants.SCREEN_SCALE;
            my *= Constants.SCREEN_SCALE;
            int saveMS = moveStuck;
            int count=0;
            int px=x,py=y;
            while (!manager.walkCheck(x + mx, y)) {
                count++;
                if (mx < 0) {
                    mx++;
                    if (mx > 0) mx = 0;
                } else {
                    mx--;
                    if (mx < 0) mx = 0;
                }
                //ax/=1.1;ay/=1.1;
                moveStuck++;
                if(count>Constants.SCREEN_WIDTH){
                    mx=0;
                    break;
                }
            }count=0;
            while (!manager.walkCheck(x, y + my)) {
                count++;
                if (my < 0) {
                    my++;
                    if (my > 0) my = 0;
                } else {
                    my--;
                    if (my < 0) my = 0;
                }
                //ax/=1.1;ay/=1.1;
                moveStuck++;
                if(count>Constants.SCREEN_WIDTH){
                    my=0;
                    break;
                }
            }

            x = px+(int)mx;
            y = py+(int)my;

            if (x < 0) {
                x = 0;
                moveStuck++;
            }
            if (x > manager.getWidth()) {
                x = manager.getWidth();
                moveStuck++;
            }
            if (y < 0) {
                y = 0;
                moveStuck++;
            }
            if (y > manager.getHeight()) {
                y = manager.getHeight();
                moveStuck++;
            }
            if (saveMS == moveStuck) if (Math.random() > 0.8) moveStuck = 0;
            //if(Math.abs(ax)+Math.abs(ay)>0.00001&&moveStuck!=0) System.out.println("=========================="+moveStuck);

        }catch (Exception e){};
    }
    public void moveRot(float rot, float spd){
        move((float) (spd*Math.cos(Math.toRadians(rot))),(float)(spd*Math.sin(Math.toRadians(rot))));
    }
    public void pushHp(float value){
        if(value<0)balloon.pop(""+(int)-value,Color.rgb(255,99,71));
        if(value>0)balloon.pop(""+(int)value,Color.rgb(173,255,47));
        hp+=value;
        if(hp>maxHp)hp=maxHp;
        if(hp<=0)die();
    }
    @Override
    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public void setAimRot(float aimRot) {
        this.aimRot=aimRot;
    }

    public void setPos(Point pos){
        x=pos.x;
        y=pos.y;
    }
    public Point getPos(){
        return new Point(x,y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void capScreen(){
        Constants.DRAG_DIST.x=-x+Constants.SCREEN_WIDTH/2;
        Constants.DRAG_DIST.y=-y+Constants.SCREEN_HEIGHT/2;
    }
    public void followScreen(){
        int tx=-x+Constants.SCREEN_WIDTH/2,ty=-y+Constants.SCREEN_HEIGHT/2;
        if(Constants.DRAG_DIST.x!=tx)Constants.DRAG_DIST.x+=(tx-Constants.DRAG_DIST.x)/4;
        if(Constants.DRAG_DIST.y!=ty)Constants.DRAG_DIST.y+=(ty-Constants.DRAG_DIST.y)/4;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }
    public boolean isFriendly(){
        return false;
    }
    public float getMoveSPD(){
        return moveSPD;
    }
    public float getHp(){
        return hp;
    }
    public void getAttention(Point ap){

    }
}
