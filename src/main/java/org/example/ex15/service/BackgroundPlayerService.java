package org.example.ex15.service;

import org.example.ex15.object.Bubble;
import org.example.ex15.object.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

// 메인 스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨
// 백그라운드에서 계속 관찰 - 하나의 쓰레드가 되기 위해 Runnable
public class BackgroundPlayerService implements Runnable {
    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    public BackgroundPlayerService(Player player) {
        this.player = player;
        this.bubbleList = player.getBubbleList();
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
            // 1. 벽 충돌 체크
            // 색상 확인
            Color leftColor = new Color(image.getRGB(player.getX() + 5, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
            // -2가 아온다는 듯은 바닥에 색깔이 없이 흰색
            int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 1)
                    + image.getRGB(player.getX() + 50, player.getY() + 50 + 1);

//            System.out.println("leftColor : " + leftColor);
//            System.out.println("rightColor : " + rightColor);
//            System.out.println("bottomColor : " + bottomColor);

            // 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//                System.out.println("왼쪽 벽에 충돌 됨!");
                player.setLeft(false);
                player.setLeftWallCrash(true);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//                System.out.println("오른쪽 벽에 충돌 됨!");
                player.setRight(false);
                player.setRightWallCrash(true);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }

            // 바닥 충돌 확인
            if (bottomColor != -2) {
//                System.out.println("바닥에 충돌함");
                player.setDown(false);
            } else {
                if (!player.isUp() && !player.isDown()) {
                    player.down();
                }
            }

//            if(player.getY() + 2 >= 537) {
////                System.out.println("맨바닥에 충돌함");
//                player.setDown(false);
//            }

            // 2. 버블 충돌 체크
            for (int i = 0; i < bubbleList.size(); i++) {
                Bubble bubble = bubbleList.get(i);
                if (bubble.getState() == 1) {
                    if ((Math.abs(player.getX() - bubble.getX()) < 10)
                            && (0 < Math.abs(player.getY() - bubble.getY()) && Math.abs(player.getY() - bubble.getY()) < 50)) {
                        bubble.clearBubbled();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
