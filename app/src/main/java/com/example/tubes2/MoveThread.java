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
    protected ThreadHandler threadHandler;
    private boolean flag = true;
    private int screenWidth; // lebar layar untuk collision
    private int screenHeight;
    private Player player;
    protected List<Bullet> bullet;
    private List<Enemy> enemy;
    private PowerUp powerUp;
//    private ThreadForEnemy threadForEnemy;
//    private ThreadForBullet threadForBullet;

    public MoveThread(Player player, List<Bullet> bullet, List<Enemy> enemy, PowerUp powerUp, ThreadHandler threadHandler, int width,int height) {
        this.thread = new Thread(this);
        this.threadHandler = threadHandler;
        this.player = player;
        this.screenWidth = width;
        this.screenHeight = height;
        this.bullet = bullet;
        this.enemy = enemy;
        this.powerUp = powerUp;
//        this.threadForBullet = new ThreadForBullet();
//        this.threadForEnemy = new ThreadForEnemy();
    }

    public void init() {
        this.thread.start();
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.nanoTime();
            for(int i=0;i<this.bullet.size();i++) {
                int[] n2 = new int[2];
                n2[0] = this.bullet.get(i).getX();
                n2[1] = this.bullet.get(i).getY();
                threadHandler.drawBackground(n2);
                this.bullet.get(i).move();
                if (bullet.get(i).getY() <= 0) {
                    bullet.remove(i);
                }
                else {
                    n2 = new int[2];
                    n2[0] = this.bullet.get(i).getX();
                    n2[1] = this.bullet.get(i).getY();
                    this.threadHandler.drawBullet(n2);
                }
            }
            int[] n2 = new int[2];
            n2[0] = powerUp.getX();
            n2[1] = powerUp.getY();
            threadHandler.drawBackground(n2);
            this.powerUp.move();
            n2 = new int[2];
            n2[0] = powerUp.getX();
            n2[1] = powerUp.getY();
            this.threadHandler.drawPowerUp(n2);
            for (int i = 0; i < this.enemy.size(); i++) {
                n2 = new int[3];
                n2[0] = this.enemy.get(i).getX();
                n2[1] = this.enemy.get(i).getY();
                threadHandler.drawBackground(n2);
                this.enemy.get(i).move();
                if (enemy.get(i).getY() >= screenHeight) {
                    enemy.remove(i);
                }
                else {
                    n2 = new int[3];
                    n2[0] = this.enemy.get(i).getX();
                    n2[1] = this.enemy.get(i).getY();
                    n2[2] = this.enemy.get(i).getType();
                    this.threadHandler.drawEnemy(n2);
                }
            }

            int[] n = new int[2];
            n[0] = this.player.getX();
            n[1] = this.player.getY();
            threadHandler.drawBackground(n);
            this.player.move();
            n = new int[2];
            n[0] = this.player.getX();
            n[1] = this.player.getY();
            this.threadHandler.draw(n);
            if (player.getX() <= 0 || player.getX() >= screenWidth) {
                player.setVelocity(0);
            }

            try {
                threadHandler.invalidateCanvas();
                Thread.sleep(1000/30 - (System.nanoTime()-start)/1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private class ThreadForBullet implements Runnable {
//        Thread thread;
//
//        public ThreadForBullet() {
//        }
//
//        public void start() {
//            this.thread = new Thread(this);
//            this.thread.start();
//        }
//
//        public Thread getThread() {
//            return thread;
//        }
//
//        @Override
//        public void run() {
//            int[] n;
//            for (int i = 0; i < bullet.size(); i++) {
//                bullet.get(i).move();
//                n = new int[2];
//                n[0] = bullet.get(i).getX();
//                n[1] = bullet.get(i).getY();
//                threadHandler.drawBullet(n);
//            }
//        }
//    }
//
//    private class ThreadForEnemy implements Runnable {
//        Thread thread;
//
//        public ThreadForEnemy() {
//        }
//
//        public void start() {
//            this.thread = new Thread(this);
//            this.thread.start();
//        }
//
//        public Thread getThread() {
//            return thread;
//        }
//
//        @Override
//        public void run() {
//            int[] n;
//            for (int i = 0; i < enemy.size(); i++) {
//                enemy.get(i).move();
//                n = new int[3];
//                n[0] = enemy.get(i).getX();
//                n[1] = enemy.get(i).getY();
//                n[2] = enemy.get(i).getType();
//                threadHandler.drawEnemy(n);
//            }
//        }
//    }
}
