package com.example.tubes2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tubes2.model.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Player message = 0
 *
 * bullet message =1
 * Enemy message = 2
 */
public class ThreadHandler extends Handler {
    private IMainActivity iMainActivity;

    public ThreadHandler(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
    }
    @Override
    public void handleMessage(Message msg){
        if(msg.what == 0){
            int[] n = (int[])msg.obj;
            Log.d("handle message",n[0]+" "+n[1]);
            iMainActivity.drawPlayer(n[0],n[1]);
        }
        else if(msg.what == 1){
            int[] n =(int[])msg.obj;
            this.iMainActivity.drawBullet(n[0],n[1]);
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
}
