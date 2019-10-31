package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;
import com.example.tubes2.model.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class MoveThread implements Runnable {
    private Thread thread;
    private ThreadHandler threadHandler;
    private boolean flag = true;
    private int screenWidth; // lebar layar untuk collision
    private int screenHeight;
    private Player player;
    private List<Bullet> bullet;
    private List<Enemy> enemy;
    private PowerUp powerUp;

    public MoveThread(Player player, List<Bullet> bullet, List<Enemy> enemy,PowerUp powerUp, ThreadHandler threadHandler, int width,int height) {
        this.thread = new Thread(this);


        this.threadHandler = threadHandler;
        this.player = player;
        this.screenWidth = width;
        this.screenHeight = height;
        this.bullet = bullet;
        this.enemy = enemy;
        this.powerUp = powerUp;
    }

    public void init() {
        thread.start();
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.nanoTime();
            //player.x+velocity
            this.player.move();
            int[] n = new int[2];
            n[0] = this.player.getX();
            n[1] = this.player.getY();
            this.threadHandler.draw(n);
            for (int i = 0; i < this.bullet.size(); i++) {
                if (bullet.get(i).getY() <= 0) {
                    bullet.remove(i);
                }
                else {
                    this.bullet.get(i).move();
                    n = new int[2];
                    n[0] = this.bullet.get(i).getX();
                    n[1] = this.bullet.get(i).getY();
                    this.threadHandler.drawBullet(n);
                }
            }
            for (int i = 0; i < this.enemy.size(); i++) {
                if (enemy.get(i).getY() >= screenHeight) {
                    // musuh dipojok screen
                    enemy.remove(i);
                }
                else {
                    this.enemy.get(i).move();
                    n = new int[3];
                    n[0] = this.enemy.get(i).getX();
                    n[1] = this.enemy.get(i).getY();
                    n[2] = this.enemy.get(i).getType();
                    this.threadHandler.drawEnemy(n);
                }
            }
            powerUp.move();
            n = new int[2];
            n[0] = powerUp.getX();
            n[1] = powerUp.getY();
            threadHandler.drawPowerUp(n);
            if (player.getX() <= 0 || player.getX() >= screenWidth) {
                //player mentok dengan screen
                player.setVelocity(0);
            }
            this.threadHandler.invalidateCanvas();
            try {
                Thread.sleep(1000 / 30 - (System.nanoTime() - start) / 1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
