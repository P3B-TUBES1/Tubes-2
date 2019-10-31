package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveThread implements Runnable {
    private final Thread thread3;
    private final Thread thread2;
    private Thread thread;
    private ThreadHandler threadHandler;
    private boolean flag = true;
    private int screenWidth; // lebar layar untuk collision
    private Player player;
    private List<Bullet> bullet;
    private List<Enemy> enemy;

    public MoveThread(Player player, List<Bullet> bullet, List<Enemy> enemy, ThreadHandler threadHandler, int width) {
        this.thread = new Thread(this);
        this.thread2 = new Thread(this);
        this.thread3 = new Thread(this);


        this.threadHandler = threadHandler;
        this.player = player;
        this.screenWidth = width;
        this.bullet = bullet;
        this.enemy = enemy;
    }

    public void init() {
        thread.start();
        thread2.start();
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.nanoTime();
            this.player.move();
            int[] n = new int[2];
            n[0] = this.player.getX();
            n[1] = this.player.getY();
            Log.d("how many bullet", this.bullet.size() + "");

            this.threadHandler.draw(n);
            for (int i = 0; i < this.bullet.size(); i++) {
                this.bullet.get(i).move();
                n = new int[2];
                n[0] = this.bullet.get(i).getX();
                n[1] = this.bullet.get(i).getY();
                this.threadHandler.drawBullet(n);
            }
            for (int i = 0; i < this.enemy.size(); i++) {
                this.enemy.get(i).move();
                n = new int[3];
                n[0] = this.enemy.get(i).getX();
                n[1] = this.enemy.get(i).getY();
                n[2] = this.enemy.get(i).getType();
                this.threadHandler.drawEnemy(n);
            }
            this.threadHandler.invalidateCanvas();
            if (player.getX() <= 0 || player.getX() >= screenWidth) {
                player.setVelocity(0);
            }
            try {
                Thread.sleep(1000 / 30 - (System.nanoTime() - start) / 1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
