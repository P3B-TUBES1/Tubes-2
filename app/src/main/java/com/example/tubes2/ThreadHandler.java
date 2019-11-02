package com.example.tubes2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tubes2.model.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 */
public class ThreadHandler extends Handler {
    private IMainActivity iMainActivity;

    public ThreadHandler(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
    }
    @Override
    public void handleMessage(Message msg){
        int[] n;
        switch(msg.what){
            case 0:
                n = (int[])msg.obj;
                iMainActivity.drawPlayer(n[0],n[1]);
                break;
            case 1:
                n =(int[])msg.obj;
                this.iMainActivity.drawBullet(n[0],n[1]);
                break;
            case 2:
                n =(int[])msg.obj;
                this.iMainActivity.drawEnemy(n[0],n[1],n[2]);
                break;
            case 3:
                n =(int[])msg.obj;
                this.iMainActivity.drawPowerUp(n[0],n[1]);
                break;
            case 4:
                this.iMainActivity.gameOver();
                break;
            case 5:
                this.iMainActivity.writeScore((int)msg.obj);
                break;
            case -1:
                this.iMainActivity.resetCanvas();
                break;

        }
    }
    public void draw(int[] n){
        Message msg = new Message();
        msg.what = 0; // value for player
        msg.obj = n;
        this.sendMessage(msg);
    }
    public void drawBullet(int[] n ){
        Message msg = new Message();
        msg.what = 1; // value for bullet
        msg.obj = n;
        this.sendMessage(msg);
    }

    public void drawEnemy(int[] n ){
        Message msg = new Message();
        msg.what = 2; // value for enemy
        msg.obj = n;
        this.sendMessage(msg);
    }
    public void drawPowerUp(int[] n){
        Message msg = new Message();
        msg.what = 3; // value for power up
        msg.obj = n;
        this.sendMessage(msg);
    }
    public void invalidateCanvas(){
        Message msg = new Message();
        msg.what= -1;
        this.sendMessage(msg);
    }
    public void gameOver(){
        Message msg = new Message();
        msg.what= 4;
        this.sendMessage(msg);
    }
    public void writeScore(int n){
        Message msg = new Message();
        msg.what= 5;
        msg.obj = n;
        this.sendMessage(msg);
    }
}
