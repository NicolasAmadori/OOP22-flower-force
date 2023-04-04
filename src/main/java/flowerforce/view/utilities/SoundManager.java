package flowerforce.view.utilities;

import flowerforce.common.ResourceFinder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public final class SoundManager {

    private static final float MAIN_THEME_VOLUME = -20f;
    private static final float SOUND_EFFECT_VOLUME = -10f;
    private static final String SOUND_FILE_EXTENSION = ".wav";

    private static final String MAIN_THEME_FILE_NAME = "mainTheme" + SOUND_FILE_EXTENSION;
    private static final String ZOMBIES_SIREN_FILE_NAME = "zombiesSiren" + SOUND_FILE_EXTENSION;
    private static final String SHOP_OPENING_FILE_NAME = "shopEffect" + SOUND_FILE_EXTENSION;
    private static final String SHOVEL_FILE_NAME = "shovel" + SOUND_FILE_EXTENSION;
    private static final String CARD_SELECTED_FILE_NAME = "cardSelected" + SOUND_FILE_EXTENSION;
    private static final String GROAN_FILE_NAME = "zombieGroan" + SOUND_FILE_EXTENSION;
    private static final String PLANT_PLACED_FILE_NAME = "plantPlaced" + SOUND_FILE_EXTENSION;

    public static void startMainTheme() {
        playLoopSound(ResourceFinder.getSoundPath(MAIN_THEME_FILE_NAME));
    }

    public static void zombiesAreComing() {
        playSoundEffect(ResourceFinder.getSoundPath(ZOMBIES_SIREN_FILE_NAME));
    }

    public static void openShop() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOP_OPENING_FILE_NAME));
    }

    public static void useShovel() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOVEL_FILE_NAME));
    }

    public static void cardSelected() {
        playSoundEffect(ResourceFinder.getSoundPath(CARD_SELECTED_FILE_NAME));
    }

    public static void plantPlaced() {
        playSoundEffect(ResourceFinder.getSoundPath(PLANT_PLACED_FILE_NAME));
    }

    public static void zombieGroan() {
        playSoundEffect(ResourceFinder.getSoundPath(GROAN_FILE_NAME));
    }

    public static void zombieEating() {
        playSoundEffect(ResourceFinder.getSoundPath("zombieEating" + SOUND_FILE_EXTENSION));
    }

    public static void bulletShot() {
        playSoundEffect(ResourceFinder.getSoundPath("bulletShot" + SOUND_FILE_EXTENSION));
    }

    public static void bulletHit() {
        playSoundEffect(ResourceFinder.getSoundPath("bulletImpact" + SOUND_FILE_EXTENSION));
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
