package org.example.ex15.service;


import org.example.ex15.object.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// 메인 스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨
// 백그라운드에서 계속 관찰 - 하나의 쓰레드가 되기 위해 Runnable
public class BackgroundEnemyService implements Runnable {
    private BufferedImage image;
    private Enemy enemy;

    public BackgroundEnemyService(Enemy enemy) {
        this.enemy = enemy;
        try {
            image = ImageIO.read(new File("src/main/resources/image/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (enemy.getState() == 0) {
            // 1. 벽 충돌 체크
            // 색상 확인
            Color leftColor = new Color(image.getRGB(enemy.getX() + 5, enemy.getY() + 25));
            Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
            // -2가 나온다는 듯은 바닥에 색깔이 없이 흰색
            int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 1)
                    + image.getRGB(enemy.getX() + 50, enemy.getY() + 50 + 1);

            // 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                enemy.setLeft(false);
                if (!enemy.isRight()) {
                    enemy.right();
                }
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//                System.out.println("오른쪽 벽에 충돌 됨!");
                enemy.setRight(false);
                if (!enemy.isLeft()) {
                    enemy.left();
                }
            }

            // 바닥 충돌 확인
            if (bottomColor != -2) {
//                System.out.println("바닥에 충돌함");
                enemy.setDown(false);
            } else {
                if (!enemy.isUp() && !enemy.isDown()) {
                    enemy.down();
                }
            }

//            if(enemy.getY() + 2 >= 537) {
////                System.out.println("맨바닥에 충돌함");
//                enemy.setDown(false);
//            }
        }
    }
}
