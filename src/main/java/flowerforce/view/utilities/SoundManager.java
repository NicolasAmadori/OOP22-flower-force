package flowerforce.view.utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.List;

public final class SoundManager {

    private static final float MAIN_THEME_VOLUME = -30f;
    private static final float SOUND_EFFECT_VOLUME = -20f;
    private static final List<String> PATHS = List.of(
            "C:\\Users\\nicol\\Downloads\\OOP22-flower-force\\src\\main\\resources\\flowerforce\\game\\sounds\\main_theme.wav"
    );

    public static void startMainTheme() {
        playLoopSound(PATHS.get(0));
    }

    public static void placePlant() {
        playSoundEffect(PATHS.get(0));
    }

    private static void playLoopSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(MAIN_THEME_VOLUME);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playSoundEffect(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(SOUND_EFFECT_VOLUME);
            clip.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
