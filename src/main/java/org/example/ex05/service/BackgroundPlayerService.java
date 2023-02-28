package org.example.ex05.service;

import org.example.ex05.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// 메인 스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨
// 백그라운드에서 계속 관찰 - 하나의 쓰레드가 되기 위해 Runnable
public class BackgroundPlayerService implements Runnable {
    private BufferedImage image;
    private Player player;

    public BackgroundPlayerService(Player player) {
        this.player = player;
        try {
            image = ImageIO.read(new File("src/main/resources/image/backgroundMapService.png"));
//            image = ImageIO.read(new File("src/main/resources/image/test.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            // 색상 확인
            Color leftColor = new Color(image.getRGB(player.getX() + 5, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
//            System.out.println("leftColor : " + leftColor);
//            System.out.println("rightColor : " + rightColor);
            
            if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                System.out.println("왼쪽 벽에 충돌 됨!");
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                System.out.println("오른쪽 벽에 충돌 됨!");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
