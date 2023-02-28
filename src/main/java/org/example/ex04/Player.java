package org.example.ex04;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

// class Player -> new 가는한 애들!! 게임에 존재할 수 있음(추상메소드를 가질 수 없다.)
@Getter
@Setter
public class Player extends JLabel implements Moveable {
    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private final int SPEED = 4;
    private final int JUMP_SPEED = 2;

    private ImageIcon playerR, playerL;

    public Player() {
        initObject();
        initSetting();
    }

    private void initObject() {
        playerR = new ImageIcon("src/main/resources/image/playerR.png");
        playerL = new ImageIcon("src/main/resources/image/playerL.png");
    }

    private void initSetting() {
        x = 55;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    @Override
    public void left() {
        System.out.println("left");
        left = true;
        new Thread(() -> {
            while (left) {

                setIcon(playerL);
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
        System.out.println("right");
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(playerR);
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
        System.out.println("up");
        up = true;
        new Thread(() -> {
            for(int i = 0; i < 130 / JUMP_SPEED; i++) {
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
        System.out.println("down");
        down = true;
        new Thread(() -> {
            for(int i = 0; i < 130 / JUMP_SPEED; i++) {
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
