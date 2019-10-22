package com.example.tubes2;

import android.util.Log;

import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveThread implements Runnable{
    private Thread thread;
    private IMainActivity iMainActivity;
    private ThreadHandler threadHandler;
    private boolean flag = true;
    private Player player;
    private List<Bullet> bullet;
    private List<Enemy> enemy;
    public MoveThread(Player player, List<Bullet> bullet, List<Enemy> enemy,IMainActivity iMainActivity){
        this.thread = new Thread(this);
        this.iMainActivity = iMainActivity;
        this.threadHandler = new ThreadHandler(iMainActivity);
        this.player = player;
        this.bullet = bullet;
        this.enemy = enemy;
    }
    public void init(){
        this.thread.start();
    }
    @Override
    public void run() {
        while(flag){
            this.player.move();
            int[] n = new int[2];
            n[0] = this.player.getX();
            n[1] = this.player.getY();
            Log.d("how many bullet",this.bullet.size()+"");

            this.threadHandler.draw(n);
            for(int i=0;i<this.bullet.size();i++) {
                this.bullet.get(i).move();
                n = new int[2];
                n[0] = this.bullet.get(i).getX();
                n[1] = this.bullet.get(i).getY();
                this.threadHandler.drawBullet(n);
            }
            try{
                Thread.sleep(1000/30);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
