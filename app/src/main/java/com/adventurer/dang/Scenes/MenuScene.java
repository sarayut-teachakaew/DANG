package com.adventurer.dang.Scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;

import com.adventurer.dang.Backpack;
import com.adventurer.dang.Balloon;
import com.adventurer.dang.Buttons.RectButton;
import com.adventurer.dang.Buttons.TextureButton;
import com.adventurer.dang.Card;
import com.adventurer.dang.Constants;
import com.adventurer.dang.Text;

import static com.adventurer.dang.Backpack.COIN;
import static com.adventurer.dang.Backpack.score;

/**
 * Created by x_x on 4/11/2560.
 */

public class MenuScene implements Scene {
    private SceneManager manager;
    private RectButton playButton;
    TextureButton coinBut;
    Card newCard=null;Boolean openCard=false;
    Balloon ball;

    public MenuScene(SceneManager manager){
        this.manager = manager;
        setButton();
        ball = new Balloon();

    }
    public void setButton(){
        playButton = new RectButton(Constants.SCREEN_SCALE*300,Constants.SCREEN_SCALE*200
                ,new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2)
                ,Color.rgb(176,196,222),"PLAY",Color.rgb(240,255,255));

        int cWi=(int)(Constants.SCREEN_SCALE*300),cHi=(int)(Constants.SCREEN_SCALE*300);
        coinBut = new TextureButton(new Point(Constants.SCREEN_WIDTH-cWi,Constants.SCREEN_HEIGHT-cHi),cWi,cHi,SceneManager.pic.coin_pic);
        coinBut.panPic=false;
    }
    public void update(){
        if(!openCard&&newCard!=null&& newCard.isDest()){
            newCard=null;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.rgb(245,255,250));

        playButton.draw(canvas);
        coinBut.draw(canvas);

        if(newCard!=null)newCard.draw(canvas);

        ball.draw(canvas);

        Text coinTxt = new Text(new Point((int)(Constants.SCREEN_WIDTH-Constants.SCREEN_SCALE*50),(int)(Constants.SCREEN_HEIGHT-Constants.SCREEN_SCALE*50))
                ,(int) COIN+"",Color.rgb(0,0,0),Constants.SCREEN_SCALE*300);
        coinTxt.draw(canvas);

        Text scoreTxt = new Text(new Point((int)(Constants.SCREEN_WIDTH/2),(int)(Constants.SCREEN_HEIGHT-Constants.SCREEN_SCALE*80))
                ,"Best Score : "+(int) score,Color.rgb(0,0,0),Constants.SCREEN_SCALE*300);
        scoreTxt.draw(canvas);



    }
    public void terminate(){
        setButton();
    }
    public void recieveTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ball.setPos(event.getX(),event.getY());
                if(coinBut.hitCheck(new Point((int)event.getX(),(int)event.getY())))ball.pop("โฮ่ง");
                else ball.pop("POP");
                //ball.addText("ไม่อยากกินต้มไก่");
                break;
            case MotionEvent.ACTION_UP:

                if(openCard&&newCard!=null){
                    newCard.setDestPos(Constants.SCREEN_WIDTH/4,-newCard.height);
                    openCard = false;

                }
                if(coinBut.hitCheck(new Point((int)event.getX(),(int)event.getY()))){
                    if(COIN > 0) {
                        COIN--;
                        newCard = new Card();
                        newCard.width = (int) (Constants.SCREEN_SCALE * 600);
                        newCard.height = (int) (Constants.SCREEN_SCALE * 900);
                        newCard.setStartPos(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT + newCard.height / 2);
                        newCard.setDestPos(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 2);
                        Backpack.addCard(newCard);
                        openCard=true;
                    }



                }
                else if(playButton.hitCheck(new Point((int)event.getX(),(int)event.getY()))) {
                    manager.reGame();
                    manager.activeScene = SceneManager.GAME_SCENE;
                    terminate();
                }
                break;

        }
    }
}
