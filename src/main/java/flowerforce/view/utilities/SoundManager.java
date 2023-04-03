package flowerforce.view.utilities;

import flowerforce.common.ResourceFinder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.List;

public final class SoundManager {

    private static final float MAIN_THEME_VOLUME = -20f;
    private static final float SOUND_EFFECT_VOLUME = -10f;

    private static final String MAIN_THEME_FILE_NAME = "main_theme.wav";
    private static final String GAME_START_FILE_NAME = "start_game_siren_with_voice.wav";
    private static final String SHOP_OPENING_FILE_NAME = "shop_effect.wav";
    public static void startMainTheme() {
        playLoopSound(ResourceFinder.getSoundPath(MAIN_THEME_FILE_NAME));
    }

    public static void startGameSound() {
        playSoundEffect(ResourceFinder.getSoundPath(GAME_START_FILE_NAME));
    }

    public static void openShopSound() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOP_OPENING_FILE_NAME));
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
