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
        if(msg.what == 0){
            ObjHolder objHolder = (ObjHolder) msg.obj;
            iMainActivity.drawPlayer(objHolder.getN(),objHolder.getN2());
        }

    }
    public void draw(int[] n,int[][] n2){
        Message msg = new Message();
        msg.what = 0; // value for player
        msg.obj = new ObjHolder(n,n2);
        this.sendMessage(msg);
    }

    /*
     * obj holder menyimpan file x dan y untuk player,enemy,bullet
     */
    private class ObjHolder{
        private int[] n;
        private int[][] n2;
        public ObjHolder(int[] n,int[][] n2){
            this.n = n;
            this.n2 = n2;
        }
        public int[] getN(){
            return this.n;
        }
        public int[][] getN2(){
            return this.n2;
        }
    }
}
