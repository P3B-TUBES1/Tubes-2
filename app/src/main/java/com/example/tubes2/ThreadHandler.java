package com.example.tubes2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/*
 * Player message = 0
 * Enemy message = 1
 * bullet message =2
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
    }
    public void drawPlayer(int[] n){
        Message msg = new Message();
        msg.what = 0; // value for player
        msg.obj = n;
        this.sendMessage(msg);
    }
}
