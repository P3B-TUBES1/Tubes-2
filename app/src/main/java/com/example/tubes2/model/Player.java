package com.example.tubes2.model;


import android.util.Log;

import com.example.tubes2.ThreadHandler;

/*
    player model with thread
 */
public class Player{
    private int x;
    private int y;
    public final static int size = 50; // size dari player
    private int velocity;
    public Player(int x,int y){
        this.x = x;
        this.y = y;

    }
    public void setVelocity(int n){
        this.velocity = n;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getVelocity(){
        return this.velocity;
    }
    public void move(){
        this.x = this.x+this.velocity;
    }
}
