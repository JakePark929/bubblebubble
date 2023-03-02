package org.example.ex12.object;

import lombok.Getter;
import lombok.Setter;
import org.example.ex12.BubbleFrame;
import org.example.ex12.Movable;
import org.example.ex12.service.BackgroundBubbleService;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Movable {
    // 플레이어 의존성 컴포지션
    private BubbleFrame mContext;
    private Player player;
    private Enemy enemy;
    private BackgroundBubbleService backgroundBubbleService;

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

    public Bubble(BubbleFrame mContext) {
        this.mContext = mContext;
        this.player = mContext.getPlayer();
        this.enemy = mContext.getEnemy();
        initObject();
        initSetting(player);
    }

    private void initObject() {
        bubble = new ImageIcon("src/main/resources/image/bubble.png");
        bubbled = new ImageIcon("src/main/resources/image/bubbled.png");
        bomb = new ImageIcon("src/main/resources/image/bomb.png");

        backgroundBubbleService = new BackgroundBubbleService(this);
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

    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);

            if (backgroundBubbleService.leftWall()) {
                left = false;
                break;
            }

            // 적과 물방울의 충돌 계산
            if ((Math.abs(x - enemy.getX()) < 10)
                    && (0 < Math.abs(y - enemy.getY()) && Math.abs(y - enemy.getY()) < 50)) {
                if (enemy.getState() == 0) {
                    attack();
                    break;
                }
            }

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
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);

            if (backgroundBubbleService.rightWall()) {
                right = false;
                break;
            }

            // 적과 물방울의 충돌 계산
            if ((Math.abs(x - enemy.getX()) < 10)
                    && (0 < Math.abs(y - enemy.getY()) && Math.abs(y - enemy.getY()) < 50)) {
                if (enemy.getState() == 0) {
                    attack();
                    break;
                }
            }

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

            if (backgroundBubbleService.topWall()) {
                up = false;
                break;
            }

            try {
                if (state == 0) { // 기본 물방울
                    Thread.sleep(1);
                } else { // 적을 가둔 물방울
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (state == 0) {
            clearBubble(); // 천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
        }
    }

    @Override
    public void attack() {
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        mContext.remove(enemy); // 메모리에서 사라지게 한다 (가비지 컬렉션 -> 즉시 발동하지 않음)
        mContext.repaint(); // 화면 갱신
    }

    // 행위 -> clear(동사) -> bubble (목적어)
    private void clearBubble() {
        try {
            Thread.sleep(3000);
            setIcon(bomb);
            Thread.sleep(500);
            mContext.remove(this); // BubbleFrame 의 bubble 이 메모리에서 소멸된다.
            mContext.repaint(); // BubbleFrame 의 전체를 다시 그린다.(메모리에서 없는건 그리지 않음)
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
