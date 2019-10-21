package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Player;

public class Presenter {
    private IMainActivity iMainActivity;
    private Player player;
    private int imWidth;// nilai width image view
    private int imHeight;//nilai height image view
    private ThreadHandler threadHandler;
    public Presenter(IMainActivity iMainActivity,int imWidth,int imHeight){
        this.iMainActivity = iMainActivity;
        this.threadHandler = new ThreadHandler(iMainActivity);
        this.imWidth = imWidth;
        this.imHeight = imHeight;
        this.initializePlayer();
    }

    public void initializePlayer(){
        this.player = new Player(threadHandler,this.imWidth/2,imHeight-Player.size*3);
        this.player.draw();
    }
    public void movePlayer(float x,float y){
        Log.d("test","outside if");
        if(x > this.imWidth/2){
            this.player.setVelocity(20);
        }
        else{

            this.player.setVelocity(-20);
        }
    }
    public void stopMovePlayer(){
        this.player.setVelocity(0);
    }


}
