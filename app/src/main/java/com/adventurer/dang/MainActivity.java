package com.adventurer.dang;

import android.content.ComponentCallbacks2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements ComponentCallbacks2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.TILE_SIZE=2*Constants.SCREEN_WIDTH/14;
        Constants.CB_SIZE=1*Constants.SCREEN_WIDTH/14;
        Constants.TOWER_WIDTH=2*Constants.SCREEN_WIDTH/14;
        Constants.TOWER_HEIGHT=3*Constants.SCREEN_WIDTH/14;
        Constants.CARD_WIDTH=2*Constants.SCREEN_WIDTH/14;
        Constants.CARD_HEIGHT=3*Constants.SCREEN_WIDTH/14;

        onTrimMemory(ComponentCallbacks2.TRIM_MEMORY_COMPLETE);
        setContentView(new GamePanel(this));
    }
}
