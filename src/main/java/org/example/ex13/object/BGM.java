package org.example.ex13.object;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BGM {
    public BGM() {
        try {
            AudioInputStream ais
                    = AudioSystem.getAudioInputStream(new File("src/main/resources/sound/bgm.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            // 소리 설정
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 볼륨 조정
            gainControl.setValue(-30.0f);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
