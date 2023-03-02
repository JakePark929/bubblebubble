package org.example.ex11.object;

import lombok.Getter;
import lombok.Setter;
import org.example.ex11.BubbleFrame;
import org.example.ex11.Movable;
import org.example.ex11.constant.EnemyWay;
import org.example.ex11.constant.PlayerWay;
import org.example.ex11.service.BackgroundPlayerService;

import javax.swing.*;

// class Player -> new 가는한 애들!! 게임에 존재할 수 있음(추상메소드를 가질 수 없다.)
@Getter
@Setter
public class Enemy extends JLabel implements Movable {
    // 의존선 컴포지션
    private BubbleFrame mContext;

    // 위치 상태
    private int x;
    private int y;

    // 적군의 방향
    private EnemyWay enemyWay;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 벽에 충돌한 상태
//    private boolean leftWallCrash;
//    private boolean rightWallCrash;

    // 적군 속도 상태
    private final int SPEED = 3;
    private final int JUMP_SPEED = 1;

    private ImageIcon enemyR, enemyL;

    public Enemy(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundEnemyService();
    }

    private void initObject() {
        enemyR = new ImageIcon("src/main/resources/image/enemyR.png");
        enemyL = new ImageIcon("src/main/resources/image/enemyL.png");
    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

//        leftWallCrash = false;
//        rightWallCrash = false;

        enemyWay = enemyWay.RIGHT;
        setIcon(enemyR);
        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundEnemyService() {
//        new Thread(new BackgroundEnemyService(this)).start();
    }

    @Override
    public void left() {
//        System.out.println("left");
        enemyWay = EnemyWay.LEFT;
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(enemyL);
                x = x - SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void right() {
//        System.out.println("right");
        enemyWay = EnemyWay.RIGHT;
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(enemyR);
                x = x + SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void up() {
//        System.out.println("up");
        up = true;
        new Thread(() -> {
            for(int i = 0; i < 150 / JUMP_SPEED; i++) {
                y = y - JUMP_SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            up = false;
            down();
        }).start();
    }

    @Override
    public void down() {
//        System.out.println("down");
        down = true;
        new Thread(() -> {
            while(down) {
                y = y + JUMP_SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            down = false;
        }).start();
    }
}
