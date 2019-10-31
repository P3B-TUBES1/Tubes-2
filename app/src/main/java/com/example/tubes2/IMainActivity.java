package com.example.tubes2;

public interface IMainActivity {
    void drawPlayer(int x,int y); // gambar player
    void drawBullet(int x,int y); // gambar bullet
    void drawEnemy(int x,int y,int type);
    void resetCanvas(); // invalidate
    void drawPowerUp(int x,int y);
}
