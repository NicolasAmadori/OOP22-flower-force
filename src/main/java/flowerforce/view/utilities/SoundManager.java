package flowerforce.view.utilities;

import flowerforce.common.ResourceFinder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * The class that starts and manage sounds effect and main theme.
 */
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

    private SoundManager() {

    }

    /**
     * Start the main theme (in loop).
     */
    public static void startMainTheme() {
        playLoopSound(ResourceFinder.getSoundPath(MAIN_THEME_FILE_NAME));
    }

    /**
     * Start the zombiesAreComing effect.
     */
    public static void zombiesAreComing() {
        playSoundEffect(ResourceFinder.getSoundPath(ZOMBIES_SIREN_FILE_NAME));
    }

    /**
     * Start the openShop effect.
     */
    public static void openShop() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOP_OPENING_FILE_NAME));
    }

    /**
     * Start the useShovel effect.
     */
    public static void useShovel() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOVEL_FILE_NAME));
    }

    /**
     * Start the cardSelected effect.
     */
    public static void cardSelected() {
        playSoundEffect(ResourceFinder.getSoundPath(CARD_SELECTED_FILE_NAME));
    }

    /**
     * Start the plantPlaced effect.
     */
    public static void plantPlaced() {
        playSoundEffect(ResourceFinder.getSoundPath(PLANT_PLACED_FILE_NAME));
    }

    /**
     * Start the zombieGroan effect.
     */
    public static void zombieGroan() {
        playSoundEffect(ResourceFinder.getSoundPath(GROAN_FILE_NAME));
    }

    /**
     * Start the zombieEating effect.
     */
    public static void zombieEating() {
        playSoundEffect(ResourceFinder.getSoundPath("zombieEating" + SOUND_FILE_EXTENSION));
    }

    /**
     * Start the sound effect of zombie that has eaten a plant.
     */
    public static void zombieHasEaten() {
        playSoundEffect(ResourceFinder.getSoundPath("plantEaten" + SOUND_FILE_EXTENSION));
    }

    /**
     * Start the sound effect of zombie screaming when it dies
     */
    public static void zombieDied() {
        playSoundEffect(ResourceFinder.getSoundPath("zombieDiedScream" + SOUND_FILE_EXTENSION));
    }

    /**
     * Start the bulletShot effect.
     */
    public static void bulletShot() {
        playSoundEffect(ResourceFinder.getSoundPath("bulletShot" + SOUND_FILE_EXTENSION));
    }

    /**
     * Start the bulletHit effect.
     */
    public static void bulletHit() {
        playSoundEffect(ResourceFinder.getSoundPath("bulletImpact" + SOUND_FILE_EXTENSION));
    }

    private static void playLoopSound(final String path) {
        final Optional<Clip> clip = createClip(path, MAIN_THEME_VOLUME);
        if (clip.isPresent()) {
            clip.get().start();
            clip.get().loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private static void playSoundEffect(final String path) {
        final Optional<Clip> clip = createClip(path, SOUND_EFFECT_VOLUME);
        if (clip.isPresent()) {
            clip.get().start();
        }
    }

    private static Optional<Clip> createClip(final String path, final float volume) {
        try {
            final File file = new File(path);
            final AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            final Clip clip = AudioSystem.getClip();
            clip.open(ais);
            final FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(volume);
            return Optional.of(clip);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            return Optional.empty();
        }
    }
}
