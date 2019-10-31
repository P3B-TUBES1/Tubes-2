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
    protected MoveThread moveThread;
    protected BulletSpawn bulletSpawn;
    protected EnemySpawn enemySpawn;
    protected PowerUp powerUp;

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
        this.moveThread = new MoveThread(player, listOfBullet, listOfEnemy, this.powerUp,this.threadHandler, imWidth,imHeight);
        this.moveThread.init(); // start thread
        this.bulletSpawn.create(); // start thread
        this.enemySpawn.create(); // start thread
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
     * private class untuk pembuatan bullet
     */
    private class BulletSpawn implements Runnable {
        private boolean flag = true;
        private Thread thread;
        private boolean powerUp; // cek apakah dapat jurus ato tidak
        private int powerUpValue = 1; // 1 untuk non jurus dan 2 untuk jurus
        private long powerUpTime; //mencatat waktu kapan jurus pertama kali dinyalakan

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
                Bullet bullet = new Bullet(player.getX(), player.getY() - 5);
                listOfBullet.add(bullet);
                powerUp = false;
                if (powerUp) {
                    powerUpValue = 2;
/*                    try {
                        Thread.sleep(250);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    Bullet bullet2 = new Bullet(player.getX(), player.getY() - 5);
                    listOfBullet.add(bullet2); // bullet tambahan*/
                }
                try {
                    Thread.sleep(500 / powerUpValue - (System.nanoTime() - start) / 1000000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
    * kelas untuk pembuatan musuh dan juga powerUp
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
            Log.d("thread start", "Start Enemy Spawn");
        }

        @Override
        public void run() {
            while (flag) {
                long start = System.nanoTime();
                Random rng = new Random();
                Enemy enemy = new Enemy(rng.nextInt(imWidth), 0);
                listOfEnemy.add(enemy);

                if (interval > 1000) {
                    interval -= 50;
                }
                if(Math.random() > 0.8 && powerUp.getIsAbleToSpawn()){
                    powerUp.setIsAbleToSpawn(false);
                    powerUp.setX(rng.nextInt(imWidth));
                    powerUp.setY(0);
                }
                if(powerUp.getY() >= imHeight){
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
