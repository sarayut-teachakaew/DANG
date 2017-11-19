package com.adventurer.dang;

import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.NonNull;

import com.adventurer.dang.Scenes.GameScene;
import com.adventurer.dang.Tiles.Tile;
import com.adventurer.dang.Tiles.TileObject;
import com.adventurer.dang.Towers.MoneyTower;
import com.adventurer.dang.Towers.ShootTower;
import com.adventurer.dang.Towers.SparkTower;
import com.adventurer.dang.Towers.Tower;
import com.adventurer.dang.Towers.WallTower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by x_x on 6/11/2560.
 */

public class Backpack {
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Card> delC = new ArrayList<>();
    private static ArrayList<Card> addC = new ArrayList<>();
    private static boolean onOpen=false,tclose=true;
    private static Tile sTile;
    public static boolean isOpen(){return onOpen;}
    public static int dragX = 0;
    public static void openBuild(Tile tile){
        if(getWidth() / 2 < Constants.SCREEN_WIDTH / 3)dragX = 0;
        for(int i=0;i<cards.size();i++){
            cards.get(i).width=Constants.CARD_WIDTH;
            cards.get(i).height=Constants.CARD_HEIGHT;
            cards.get(i).setStartPos(((i-((float)cards.size()-1)/2)* Constants.CARD_WIDTH)+Constants.SCREEN_WIDTH/2,-Constants.CARD_HEIGHT/2);
            cards.get(i).setDestPos(((i-((float)cards.size()-1)/2)* Constants.CARD_WIDTH)+Constants.SCREEN_WIDTH/2,Constants.CARD_HEIGHT/2);
        }
        onOpen = true;
        tclose = false;
        sTile = tile;
        //dragX = 0;
    }
    public static void close(){
        if(onOpen)for(Card c:cards)c.setDestPosY(-Constants.CARD_HEIGHT);
        onOpen = false;
        sTile = null;
    }

    public static void addCard(Card card){
        addC.add(card);
    }
    public static void delCard(Card card){
        delC.add(card);
    }

    public static boolean createTower(Card card){
        if(sTile !=null) switch (card.getType()){
                case Tower.MONEY:return sTile.createTower(new MoneyTower(sTile,card));
                case Tower.WALL:return sTile.createTower(new WallTower(sTile,card));
                case Tower.SHOOT:return sTile.createTower(new ShootTower(sTile,card));
                case Tower.SPARK:return sTile.createTower(new SparkTower(sTile,card));
            }

        return false;
    }
    @NonNull
    public static Boolean click(Point CP){
        if(!onOpen)return false;
        for(int i=0;i<cards.size();i++)if(cards.get(i).hitCheck(CP)){
            if(createTower(cards.get(i))) {
                delCard(cards.get(i));
                close();
            }
            return true;
        }
        close();
        return false;
    }

    public static void update(){
        if(addC != null){
            for(Card c: addC)cards.add(c);
            Collections.sort(cards,new Comparator<Card>() {
                @Override
                public int compare(Card a, Card b)
                {
                    return  b.getRarity()-a.getRarity();
                }
            });
        }
        addC= new ArrayList<>();
        if(delC!=null)for(Card c: delC)cards.remove(c);
        delC= new ArrayList<>();
    }
    public static void draw(Canvas canvas){
        if(cards != null)if(onOpen)for(int i=0;i<cards.size();i++){
            cards.get(i).setPosX(((i-((float)cards.size()-1)/2)* Constants.CARD_WIDTH)+Constants.SCREEN_WIDTH/2+dragX);
            cards.get(i).draw(canvas);
        }else if(!tclose) for(Card c:cards) if(!c.isDest())c.draw(canvas);else tclose=true;
    }

    public static int getWidth(){return cards.size()*Constants.CARD_WIDTH;}
    public static Tile getSTile(){return sTile;}
}
