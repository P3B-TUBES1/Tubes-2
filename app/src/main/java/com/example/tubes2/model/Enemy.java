package com.example.tubes2.model;

import java.util.Random;

public class Enemy {
    private int x, y;
    private final int velocity = 5;
    private int type;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = new Random().nextInt(2);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getType() {
        return type;
    }

    public void move() {
        this.y = this.y + velocity;
    }
}
