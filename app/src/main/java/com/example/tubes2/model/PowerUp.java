package com.example.tubes2.model;

public class PowerUp {
    private int x,y;
    private final int velocity =10;
    private boolean isAbleToSpawn;
    public PowerUp(){
        this.isAbleToSpawn = true;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean getIsAbleToSpawn() {
        return isAbleToSpawn;
    }
    public void move(){
        this.y = this.y+velocity;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setIsAbleToSpawn(boolean isAbleToSpawn) {
        this.isAbleToSpawn= isAbleToSpawn;
    }
}