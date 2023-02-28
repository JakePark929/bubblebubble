package org.example.ex07;

import org.example.ex07.object.Bubble;
import org.example.ex07.object.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// 윈도우 창이 되었음
// 2. 윈도우 창은 내부에 패널을 하나 가지고 있다.
public class BubbleFrame extends JFrame {
    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() throws HeadlessException {
        initObject();
        initSetting();
        initListener();
        setVisible(true); // 그림을 그려라
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon("src/main/resources/image/backgroundMap.png"));
//        backgroundMap = new JLabel(new ImageIcon("src/main/resources/image/backgroundMapService.png"));
//        backgroundMap = new JLabel(new ImageIcon("src/main/resources/image/test.png"));
        setContentPane(backgroundMap); // pane 없이 바로 label 생성
        player = new Player();
        add(player);
    }

    private void initSetting() {
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃(자유롭게 그림을 그릴 수 있다.)

        setLocationRelativeTo(null); // JFrame 디스플레이 가운데 배치하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼으로 창을 끌 때 JVM 같이 종료하기
    }

    private void initListener() {
        addKeyListener(new KeyAdapter() {

            // 키보드 클릭 이벤트
            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyCode());

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        Bubble bubble = new Bubble(player);
                        add(bubble);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        new BubbleFrame();
    }
}
