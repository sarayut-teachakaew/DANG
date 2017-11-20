package com.adventurer.dang.Scenes;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Card;
import com.adventurer.dang.Pic;

import java.util.ArrayList;

/**
 * Created by x_x on 3/11/2560.
 */

public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int MENU_SCENE=0,GAME_SCENE=1;
    public int activeScene;
    public static Pic pic;

    public SceneManager(){
        pic = new Pic(-1);

        Backpack.loadCard();
        Backpack.loadCoin();
        Backpack.loadscore();

        /*Backpack.addCard(new Card(Card.MONEY_TOWER));
        Backpack.addCard(new Card(Card.WALL_TOWER));
        Backpack.addCard(new Card(Card.SHOOT_TOWER));
        Backpack.addCard(new Card(Card.SPARK_TOWER));
        Backpack.addCard(new Card(Card.MONEY_TOWER));
        Backpack.addCard(new Card(Card.WALL_TOWER));
        Backpack.addCard(new Card(Card.SHOOT_TOWER));
        Backpack.addCard(new Card(Card.SPARK_TOWER));
        Backpack.addCard(new Card(Card.MONEY_TOWER));
        Backpack.addCard(new Card(Card.WALL_TOWER));
        Backpack.addCard(new Card(Card.SHOOT_TOWER));
        Backpack.addCard(new Card(Card.SPARK_TOWER));
        Backpack.addCard(new Card(Card.MONEY_TOWER));
        Backpack.addCard(new Card(Card.WALL_TOWER));
        Backpack.addCard(new Card(Card.SHOOT_TOWER));
        Backpack.addCard(new Card(Card.SPARK_TOWER));
        Backpack.addCard(new Card(Card.MONEY_TOWER));
        Backpack.addCard(new Card(Card.WALL_TOWER));
        Backpack.addCard(new Card(Card.SHOOT_TOWER));
        Backpack.addCard(new Card(Card.SPARK_TOWER));*/

        activeScene = 0;
        scenes.add(new MenuScene(this));
        scenes.add(null);
    }

    public void reGame(){
        scenes.set(GAME_SCENE,new GameScene(this));
    }
    public void freeGame(){
        scenes.set(GAME_SCENE,null);
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(activeScene).recieveTouch(event);
    }

    public void update() {
        scenes.get(activeScene).update();
    }
    public void draw(Canvas canvas){
        scenes.get(activeScene).draw(canvas);
    }
}
