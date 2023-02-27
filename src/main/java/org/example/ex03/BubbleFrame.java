package org.example.ex02;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// 윈도우 창이 되었음
// 2. 윈도우 창은 내부에 패널을 하나 가지고 있다.
public class BubbleFrame extends JFrame {
    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() throws HeadlessException {
        initObject();
        initSetting();
        setVisible(true); // 그림을 그려라
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon("src/main/resources/image/backgroundMap.png"));
        setContentPane(backgroundMap); // pane 없이 바로 lable 생성

        player = new Player();
        add(player);
//        backgroundMap.setSize(100, 100);
//        backgroundMap.setLocation(300, 300);
//        backgroundMap.setSize(1000, 600);
//        add(backgroundMap); // JFrame 에 JLabel 이 그려진다.
    }

    private void initSetting() {
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃(자유롭게 그림을 그릴 수 있다.)

        setLocationRelativeTo(null); // JFrame 디스플레이 가운데 배치하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼으로 창을 끌 때 JVM 같이 종료하기
    }

    public static void main(String[] args) {
        new BubbleFrame();
    }
}
