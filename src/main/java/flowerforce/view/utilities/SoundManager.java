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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * The class that starts and manage sounds effect and main theme.
 */
public final class SoundManager {

    private static final float MAIN_THEME_VOLUME = -23f;
    private static final float SOUND_EFFECT_VOLUME = -13f;
    private static final String SOUND_FILE_EXTENSION = ".wav";
    private static final String MAIN_THEME_FILE_NAME = "mainTheme" + SOUND_FILE_EXTENSION;
    private static final String ZOMBIES_SIREN_FILE_NAME = "zombiesSiren" + SOUND_FILE_EXTENSION;
    private static final String SHOP_EFFECT_FILE_NAME = "shopEffect" + SOUND_FILE_EXTENSION;
    private static final String SHOVEL_FILE_NAME = "shovel" + SOUND_FILE_EXTENSION;
    private static final String CARD_SELECTED_FILE_NAME = "cardSelected" + SOUND_FILE_EXTENSION;
    private static final String PLANT_PLACED_FILE_NAME = "plantPlaced" + SOUND_FILE_EXTENSION;
    private static final String GROAN_FILE_NAME = "zombieGroan" + SOUND_FILE_EXTENSION;
    private static final String ZOMBIE_EATING_FILE_NAME = "zombieEating" + SOUND_FILE_EXTENSION;
    private static final String PLANT_EATEN_FILE_NAME = "plantEaten" + SOUND_FILE_EXTENSION;
    private static final String ZOMBIE_DIED_FILE_NAME = "zombieDiedScream" + SOUND_FILE_EXTENSION;
    private static final String BULLET_SHOT_FILE_NAME = "bulletShot" + SOUND_FILE_EXTENSION;
    private static final String BULLET_IMPACT_FILE_NAME = "bulletImpact" + SOUND_FILE_EXTENSION;

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
     * Start the shop effect.
     */
    public static void shopEffect() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOP_EFFECT_FILE_NAME));
    }

    /**
     * Start the shovel effect.
     */
    public static void useShovel() {
        playSoundEffect(ResourceFinder.getSoundPath(SHOVEL_FILE_NAME));
    }

    /**
     * Start the selected card effect.
     */
    public static void cardSelected() {
        playSoundEffect(ResourceFinder.getSoundPath(CARD_SELECTED_FILE_NAME));
    }

    /**
     * Start the plant placed effect.
     */
    public static void plantPlaced() {
        playSoundEffect(ResourceFinder.getSoundPath(PLANT_PLACED_FILE_NAME));
    }

    /**
     * Start the zombie groan effect.
     */
    public static void zombieGroan() {
        playSoundEffect(ResourceFinder.getSoundPath(GROAN_FILE_NAME));
    }

    /**
     * Start the zombie eating effect.
     */
    public static void zombieEating() {
        playSoundEffect(ResourceFinder.getSoundPath(ZOMBIE_EATING_FILE_NAME));
    }

    /**
     * Start the zombie has eaten a plant effect.
     */
    public static void zombieHasEaten() {
        playSoundEffect(ResourceFinder.getSoundPath(PLANT_EATEN_FILE_NAME));
    }

    /**
     * Start the zombie has died effect.
     */
    public static void zombieDied() {
        playSoundEffect(ResourceFinder.getSoundPath(ZOMBIE_DIED_FILE_NAME));
    }

    /**
     * Start the bullet has been shot effect.
     */
    public static void bulletShot() {
        playSoundEffect(ResourceFinder.getSoundPath(BULLET_SHOT_FILE_NAME));
    }

    /**
     * Start the bullet hit effect.
     */
    public static void bulletHit() {
        playSoundEffect(ResourceFinder.getSoundPath(BULLET_IMPACT_FILE_NAME));
    }

    private static void playLoopSound(final URL url) {
        final Optional<Clip> clip = createClip(url, MAIN_THEME_VOLUME);
        clip.ifPresent(c -> {
            c.start();
            clip.get().loop(Clip.LOOP_CONTINUOUSLY);
        });
    }

    private static void playSoundEffect(final URL url) {
        final Optional<Clip> clip = createClip(url, SOUND_EFFECT_VOLUME);
        clip.ifPresent(Clip::start);
    }

    private static Optional<Clip> createClip(final URL url, final float volume) {
        try {
            final File file = new File(url.toURI());
            final AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            final Clip clip = AudioSystem.getClip();
            clip.open(ais);
            final FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(volume);

            return Optional.of(clip);
        } catch (URISyntaxException | UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            return Optional.empty();
        }
    }
}
