package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Presenter {
    private IMainActivity iMainActivity;
    private Player player;
    private int imWidth;// nilai width image view
    private int imHeight;//nilai height image view
    protected ThreadHandler threadHandler;
    protected List<Bullet> listOfBullet;
    protected List<Enemy> listOfEnemy;
    private MoveThread moveThread;
    private CollisionDetector collisionDetector;
    public Presenter(IMainActivity iMainActivity,int imWidth,int imHeight){
        this.iMainActivity = iMainActivity;
        this.threadHandler = new ThreadHandler(iMainActivity);
        this.imWidth = imWidth;
        this.imHeight = imHeight;
        this.listOfBullet = new LinkedList<Bullet>();
        this.listOfEnemy = new LinkedList<Enemy>();
        this.collisionDetector = new CollisionDetector();
        this.initialize();
    }

    public void initialize(){ // init game
        this.player = new Player(this.imWidth/2,imHeight-Player.size*3);
        this.moveThread = new MoveThread(player,listOfBullet,listOfEnemy,this.threadHandler);
        this.moveThread.init();
        this.collisionDetector.create();
    }
    public void movePlayer(float x,float y){
        Log.d("test","outside if");
        if(x > this.imWidth/2){
            this.player.setVelocity(20);
        }
        else{

            this.player.setVelocity(-20);
        }
    }
    public void stopMovePlayer(){
        this.player.setVelocity(0);
    }

    /*
     * private class untuk handle collision dan juga pembuatan bullet dan musuh
     */
    private class CollisionDetector implements Runnable{
        private boolean flag = true;
        private Thread thread;
        public CollisionDetector(){
            this.thread = new Thread(this);
        }
        public void create(){
            this.thread.start();
            Log.d("thread start","Start");
        }
        @Override
        public void run() {
            while (flag) {
                Bullet bullet = new Bullet(player.getX() + Player.size / 2, player.getY() - 5);
                listOfBullet.add(bullet);
                for (int i = 0; i < listOfBullet.size(); i++) {
                    if (listOfBullet.get(i).getY() <= 0) {
                        listOfBullet.remove(i);
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
