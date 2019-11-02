package com.example.tubes2;


import com.example.tubes2.model.Bullet;
import com.example.tubes2.model.Enemy;
import com.example.tubes2.model.Player;
import com.example.tubes2.model.PowerUp;

import java.util.List;

public class CollisionDetection implements Runnable{
    protected Player player;
    protected List<Bullet> listOfBullet;
    protected List<Enemy> listOfEnemy;
    protected PowerUp powerUp;
    private Thread thread;
    private boolean flag;
    private Presenter presenter;
    public CollisionDetection(Player player, List<Bullet> listOfBullet, List<Enemy> listOfEnemy,PowerUp powerUp
        ,Presenter presenter){
        this.listOfBullet = listOfBullet;
        this.listOfEnemy=listOfEnemy;
        this.thread = new Thread(this);
        this.flag = true;
        this.player = player;
        this.powerUp =powerUp;
        this.presenter = presenter;
    }
    public void start(){
        this.thread.start();
    }
    @Override
    public void run() {
        while(flag) {
                outer:for (int i = 0; i < listOfEnemy.size(); i++) {
                    for (int j = 0; j < listOfBullet.size(); j++) {

                        int x1 = listOfBullet.get(j).getX();
                        int x2 = listOfEnemy.get(i).getX();
                        if ((x1 - x2 <= 200 && x1-x2>=0) || (x2-x1 <=2 && x2-x1 >=0)) {
                            int y1 = listOfBullet.get(j).getY();
                            int y2 = listOfEnemy.get(i).getY() + 128;
                            if (y2 - y1 >= 0 && y2 - y1 < 50) {
                                //score
                                if(listOfEnemy.get(i).getType()==0){
                                    this.presenter.addScore(5);
                                }else{
                                    this.presenter.addScore(10);
                                }
                                listOfBullet.remove(j);
                                j--;
                                listOfEnemy.remove(i);
                                i--;
                                break outer;
                            }
                        }
                    }
            }
            int y1 = powerUp.getY();
            int y2 = player.getY();
            if(y1-y2<= 100 && y1-y2>=0){
                int x1 = powerUp.getX();
                int x2 = player.getX();
                if(x1-x2<=100 && x1-x2>=0){
                    presenter.activeJurus();
                }
            }
            try{
                Thread.sleep(50);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
