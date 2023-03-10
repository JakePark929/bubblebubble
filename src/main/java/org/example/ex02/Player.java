package org.example.ex02;

import javax.swing.*;

public class Player extends JLabel {
    private int x;
    private int y;

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

        setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }
}
