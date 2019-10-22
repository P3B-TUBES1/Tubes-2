package com.example.tubes2.model;

import com.example.tubes2.ThreadHandler;

public class Bullet{
    private int x,y;
    private final int velocity =-20;
    public Bullet(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void move(){
        this.y = this.y+velocity;
    }
}
