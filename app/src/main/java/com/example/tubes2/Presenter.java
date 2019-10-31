package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;
import com.example.tubes2.model.PowerUp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Presenter {
    protected IMainActivity iMainActivity;
    protected Player player;
    protected int imWidth;// nilai width image view
    protected int imHeight;//nilai height image view
    protected ThreadHandler threadHandler;
    protected List<Bullet> listOfBullet;
    protected List<Enemy> listOfEnemy;
    protected PowerUp powerUp;
    protected MoveThread moveThread;
    protected BulletSpawn bulletSpawn;
    protected EnemySpawn enemySpawn;

    public Presenter(IMainActivity iMainActivity, int imWidth, int imHeight) {
        this.iMainActivity = iMainActivity;
        this.threadHandler = new ThreadHandler(iMainActivity);
        this.imWidth = imWidth;
        this.imHeight = imHeight;
        this.listOfBullet = new LinkedList<Bullet>();
        this.listOfEnemy = new LinkedList<Enemy>();
        this.bulletSpawn = new BulletSpawn();
        this.enemySpawn = new EnemySpawn();
        this.powerUp = new PowerUp();
        this.initialize();
    }

    public void initialize() { // init game
        this.player = new Player(this.imWidth / 2, imHeight - Player.size * 3);
        this.moveThread = new MoveThread(player, listOfBullet, listOfEnemy,this.powerUp,this.threadHandler, imWidth);
        this.moveThread.init();
        this.bulletSpawn.create();
        this.enemySpawn.create();
    }

    public void movePlayer(float x, float y) {
        if (x > this.imWidth / 2 && player.getX() + 20 <= imWidth) {
            this.player.setVelocity(20);
        } else if (x <= this.imWidth / 2 && player.getX() - 20 >= 0) {

            this.player.setVelocity(-20);
        }
    }

    public void stopMovePlayer() {
        this.player.setVelocity(0);
    }

    /*
     * class untuk spawn bullet
     */
    private class BulletSpawn implements Runnable {
        private boolean flag = true;
        private Thread thread;
        private boolean powerUp; // cek apakah dapat jurus ato tidak
        private int valueForPowerUp= 1; // 1 untuk non jurus dan 2 untuk jurus
        private long jurusTime; //mencatat waktu kapan jurus pertama kali dinyalakan

        public BulletSpawn() {
            this.thread = new Thread(this);
            this.powerUp = false;
        }

        public void create() {
            this.thread.start();
        }

        @Override
        public void run() {
            while (flag) {
                long start = System.nanoTime();
                Bullet bullet = new Bullet(player.getX(), player.getY() - 25);
                listOfBullet.add(bullet);
                if (powerUp) {
                    valueForPowerUp = 2;
                }
                for (int i = 0; i < listOfBullet.size(); i++) {
                    if (listOfBullet.get(i).getY() <= 0) {
                        listOfBullet.remove(i);
                    }
                }
                try {
                    Thread.sleep(500 / valueForPowerUp - (System.nanoTime() - start) / 1000000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
        class untuk pembuatan musuh
     */
    private class EnemySpawn implements Runnable {
        private boolean flag = true;
        private Thread thread;
        private long interval;

        public EnemySpawn() {
            this.interval = 2000;
            this.thread = new Thread(this);
        }

        public void create() {
            this.thread.start();
        }

        @Override
        public void run() {
            while (flag) {
                long start = System.nanoTime();
                Random rng = new Random();
                Enemy enemy = new Enemy(rng.nextInt(imWidth), 0);
                listOfEnemy.add(enemy);
                for (int i = 0; i < listOfEnemy.size(); i++) {
                    if (listOfEnemy.get(i).getY() >= imHeight) {
                        listOfEnemy.remove(i);
                    }
                }
                if (interval > 1000) {
                    interval -= 50;
                }
                double spawnPowerup = Math.random();
                if(spawnPowerup > 0.7 && powerUp.getIsAbleToSpawn()){
                    powerUp.setX(rng.nextInt(imWidth));
                    powerUp.setY(0);
                    powerUp.setIsAbleToSpawn(false);
                }
                if(powerUp.getY()>= imHeight){
                    powerUp.setIsAbleToSpawn(true);
                }

                try {
                    Thread.sleep(interval - (System.nanoTime() - start) / 1000000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
