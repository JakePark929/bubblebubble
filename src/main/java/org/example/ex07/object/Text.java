package org.example.ex07.object;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

// class Player -> new 가는한 애들!! 게임에 존재할 수 있음(추상메소드를 가질 수 없다.)
@Getter
@Setter
public class Text extends JLabel {
    // 위치 상태
    private int x;
    private int y;
    private String text;


    public Text(String text, int x, int y) {
        initObject(text);
        initSetting(x, y);
    }

    private void initObject(String text) {
        this.text = text;
    }

    private void initSetting(int x, int y) {
        this.x = x;
        this.y = y;

        setText(text);
        setSize(150, 150);
        setLocation(x, y);
    }
}
