package com.adventurer.dang.Tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.adventurer.dang.Balloon;
import com.adventurer.dang.Boukenshas.Boukensha;
import com.adventurer.dang.Boukenshas.Charger;
import com.adventurer.dang.Boukenshas.Player;
import com.adventurer.dang.Bullet;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Towers.AllTower;
import com.adventurer.dang.Towers.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by x_x on 4/11/2560.
 */

public class TileManager {
    private Tile table[][];
    public int x,y,spwT=0;
    private Tile curTile=null;
    public Player player;
    public ArrayList<Boukensha>bks =new ArrayList<>();
    private ArrayList<Boukensha> delBou = new ArrayList<>();
    private ArrayList<Boukensha> addBou = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> delB = new ArrayList<>();
    private ArrayList<Bullet> addB = new ArrayList<>();
    public Balloon alert;

    public TileManager(int x,int y){
        this.x = x;this.y = y;
        table = new Tile[y][x];
        for(int i=0;i<y;i++)for(int j=0;j<x;j++){
            /*Point p = new Point((int)((j-(float)(x-1)/2)* Constants.TILE_SIZE)+Constants.SCREEN_WIDTH/2
                    ,(int)((i-(float)(y-1)/2)*Constants.TILE_SIZE)+Constants.SCREEN_HEIGHT/2);*/
            Point p = new Point(Constants.TILE_SIZE/2+j*Constants.TILE_SIZE,Constants.TILE_SIZE/2+i*Constants.TILE_SIZE);
            if(i==0 || j==0 ||i==y-1||j==x-1){
                table[i][j]=new SpawnTile(p,this);spwT++;
            }
            else if(j==x/2&&i==y/2){
                table[i][j]=new NormalTile(p,this);
                player = new Player(this,getWidth()/2 +((x%2==0)? Constants.TILE_SIZE/2:0)
                        ,getHeight()/2+((y%2==0)? Constants.TILE_SIZE/2:0));
                bks.add(player);
            }
            else if(Math.random()>0.18){
                table[i][j]=new NormalTile(p,this);
            }
            else table[i][j]=new BoulderTile(p,this);
        }

        //addBoukensha(new Charger(this,0,0));

        alert =new Balloon();
        alert.color = Color.rgb(255,127,80);
        alert.ranPop=(new Point(0,0));
        alert.popRotate=false;
        alert.size = Constants.SCREEN_HEIGHT/10;
    }
    public void delAllTower(){
        for(int i=0;i<y;i++)for(int j=0;j<x;j++)table[i][j].delTower();
    }
    public ArrayList<Tile> rangeTile(float px,float py,int r){
        int posX=(int)(px/Constants.TILE_SIZE),posY=(int)(py/Constants.TILE_SIZE);
        ArrayList<Tile>ans=new ArrayList<>();
        for(int i=(posY-r<0)? 0:posY-r;i<=((posY+r>=y)? y-1:posY+r);i++)
            for(int j=(posX-r<0)? 0:posX-r;j<=((posX+r>=x)? x-1:posX+r);j++)if(table[i][j].isWalkable())ans.add(table[i][j]);
        return ans;
    }
    public boolean walkCheck(float wx,float wy){
        if (wx<0||(int)(wx/Constants.TILE_SIZE)>=x)return false;
        else if (wy<0||(int)(wy/Constants.TILE_SIZE)>=y)return false;
        else return table[(int)(wy/Constants.TILE_SIZE)][(int)(wx/Constants.TILE_SIZE)].isWalkable();
    }
    public boolean walkCheck(Point CP){
        return walkCheck(CP.x,CP.y);
    }
    public boolean placeCheck(int px,int py){
        if(px>=x||py>=y)return false;
        for(Boukensha B:bks){
            if((int)(B.getX()/Constants.TILE_SIZE)==px&&(int)(B.getY()/Constants.TILE_SIZE)==py)
                return false;
        }
        return true;
    }
    public boolean placeCheck(Tile tt){
        int i=0,j=0;
        lp:for(i=0;i<y;i++)for(j=0;j<x;j++)if(table[i][j]==tt)break lp;
        if(j>=x||i>=y)return false;
        return placeCheck(j,i);
    }
    public TileObject visionCheck(float vx,float vy,TileObject own){
        if (vx<0||(int)(vx/Constants.TILE_SIZE)>=x)return null;
        else if (vy<0||(int)(vy/Constants.TILE_SIZE)>=y)return null;
        Boukensha b = hitCheck((int)vx,(int) vy);
        if(b!=null&&b!=own)return b;
        else{
            Tile t =table[(int)(vy/Constants.TILE_SIZE)][(int)(vx/Constants.TILE_SIZE)];
            if(t.getTower()!=null&&t!=own)return t.getTower();
            else return t;
        }
    }
    public AllTower towerCheck(float tx, float ty){
        if (tx<0||(int)(tx/Constants.TILE_SIZE)>=x)return null;
        else if (ty<0||(int)(ty/Constants.TILE_SIZE)>=y)return null;
        return table[(int)(ty/Constants.TILE_SIZE)][(int)(tx/Constants.TILE_SIZE)].getTower();
    }
    public Boukensha hitCheck(int hx,int hy){
        if(bks==null)return null;
        for(Boukensha B:bks){
            double rad = Math.sqrt((B.getX()-hx)*(B.getX()-hx)+(B.getY()-hy)*(B.getY()-hy));
            if(rad<=B.getWidth()/2) {
                return B;
            }
        }
        return null;
    }
    /*public Boukensha hitBouCheck(int hx,int hy,Boukensha own){
        if(bks==null)return null;
        for(Boukensha B:bks)if(B!=own){
            double rad = Math.sqrt((B.getX()-hx)*(B.getX()-hx)+(B.getY()-hy)*(B.getY()-hy));
            if(rad<=B.getWidth()/4) {
                return B;
            }
        }
        return null;
    }*/
    public void update(){
        if(addB != null)for(Bullet b: addB)bullets.add(b);
        addB= new ArrayList<>();
        if(delB!=null)for(Bullet b: delB)bullets.remove(b);
        delB= new ArrayList<>();
        if(addBou != null)for(Boukensha b: addBou)bks.add(b);
        addBou= new ArrayList<>();
        if(delBou!=null)for(Boukensha b: delBou)bks.remove(b);
        delBou= new ArrayList<>();

        for(int i=0;i<y;i++)for(int j=0;j<x;j++)table[i][j].update();
        for(Boukensha B:bks)B.update();
        for(Bullet B:bullets)B.update();
        //bks.get(1 ).capScreen();
        //System.out.println("/"+bks.size());
    }

    public void draw(Canvas canvas){
        for(int i=0;i<y;i++)for(int j=0;j<x;j++)table[i][j].drawFloor(canvas);

        ArrayList<TileObject> tOb=new ArrayList<>();
        tOb.addAll(bks);tOb.addAll(bullets);
        Collections.sort(tOb,new Comparator<TileObject>() {
            @Override
            public int compare(TileObject a, TileObject b)
            {
                return  a.getPos().y-b.getPos().y;
            }
        });
        for(int i=0;i<y;i++){
            for(int j=0;j<x;j++)table[i][j].draw(canvas);
            for(TileObject B:tOb){
                Point bPos = B.getPos();
                if(i*Constants.TILE_SIZE<=bPos.y&&bPos.y<(i+1)*Constants.TILE_SIZE)
                    if(((bPos.x-player.getX())*(bPos.x-player.getX())+(bPos.y-player.getY())*(bPos.y-player.getY()))
                            <=(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE)*(Constants.SCREEN_SCALE*Constants.VISIBLR_RANGE))B.draw(canvas);}
        }
        if(curTile != null)curTile.drawCB(canvas);
        alert.draw(canvas);
    }
    public void click(Point CP){
        if(curTile != null&&curTile.clickCB(CP))curTile=null;
        else {
            curTile=null;
            for(int i=0;i<y;i++)for(int j=0;j<x;j++)
                if(table[i][j].hitCheck(CP)) {
                    table[i][j].open();
                    curTile = table[i][j];
                }
                else table[i][j].close();
        }
    }
    public void close(){
        if(curTile!=null)curTile.close();
        curTile=null;
    }
    public void changeTile(Tile bef,Tile aft){
        for(int i=0;i<y;i++)for(int j=0;j<x;j++)if(table[i][j] == bef){
            table[i][j] = aft;
            return;
        }
    }

    public void addBullet(Bullet bullet){
        addB.add(bullet);
    }
    public void delBullet(Bullet bullet){
        delB.add(bullet);
    }
    public void addBoukensha(Boukensha bou){
        addBou.add(bou);
    }
    public void delBoukensha(Boukensha bou){
        delBou.add(bou);
    }

    public int getWidth(){
        return x*Constants.TILE_SIZE;
    }
    public int getHeight(){
        return y*Constants.TILE_SIZE;
    }

}
