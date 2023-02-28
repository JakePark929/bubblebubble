package org.example.ex07.object;

import lombok.Getter;
import lombok.Setter;
import org.example.ex07.Movable;
import org.example.ex07.constant.PlayerWay;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Movable {
    // 플레이어 의존성 컴포지션
    private Player player;

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;

    // 적군을 맞춘 상태
    private int state; // 0(물방울), 1(적을 가둔 물방울)

    private ImageIcon bubble; // 물방울
    private ImageIcon bubbled; // 적을 가둔 물방울
    private ImageIcon bomb; // 물방울이 터진상태

    public Bubble(Player player) {
        this.player = player;
        initObject();
        initSetting(player);
        initThread();
    }

    private void initObject() {
        bubble = new ImageIcon("src/main/resources/image/bubble.png");
        bubbled = new ImageIcon("src/main/resources/image/bubbled.png");
        bomb = new ImageIcon("src/main/resources/image/bomb.png");
    }

    private void initSetting(Player player) {
        left = false;
        right = false;
        up = false;

        x = player.getX();
        y = player.getY();

        setIcon(bubble);
        setSize(50, 50);

        state = 0;
    }

    private void initThread() {
        // 버블은 스레드가 하나만 필요하다.
        new Thread(() -> {
            if(player.getPlayerWay() == PlayerWay.LEFT) {
                left();
            } else {
                right();
            }
        }).start();
    }

    @Override
    public void left() {
        left = true;
        for(int i=0; i<400; i++) {
            x--;
            setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for(int i=0; i<400; i++) {
            x++;
            setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
