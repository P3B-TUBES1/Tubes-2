package com.example.tubes2.model;


import android.util.Log;

import com.example.tubes2.ThreadHandler;

/*
    player model with thread
 */
public class Player implements Runnable{
    private int x;
    private int y;
    public final static int size = 50; // size dari player
    private boolean flag = true;
    private Thread thread;
    private ThreadHandler threadHandler;
    private int velocity;
    public Player(ThreadHandler threadHandler,int x,int y){
        this.x = x;
        this.y = y;
        this.threadHandler =threadHandler;
        this.thread = new Thread(this);

    }
    public void setVelocity(int n){
        this.velocity = n;
    }
    public void draw(){
        int[] n = new int[2]; // berisi x dan y dari player
        n[0] = this.x;
        n[1] = this.y;
        this.threadHandler.drawPlayer(n);
        this.thread.start();
    }
    @Override
    public void run() {
        while(flag){
            try{
                x = x+velocity;
                int[] n = new int[2];
                n[0] = x;
                n[1] = y;
                this.threadHandler.drawPlayer(n);
                Log.d("velocity in try",velocity+"");
                Thread.sleep(1000/30);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
