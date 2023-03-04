package flowerforce.model.game;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.*;

import java.util.LinkedList;
import java.util.List;

public class GameImpl {
    private static final int TIME_TO_SPAWN_SUN = 50;
    private static final int SUN_VALUE = 50;
    private List<Plant> plants = new LinkedList<>();
    private List<Zombie> zombies = new LinkedList<>();
    private List<Bullet> bullets = new LinkedList<>();
    private final TimerImpl timer;
    private final TimerImpl sunTimer;
    private final Level level;
    private int sun;
    public GameImpl(final Level level) {
        this.level = level;
        timer = new TimerImpl(level.getTotalZombies());
        sunTimer = new TimerImpl(TIME_TO_SPAWN_SUN);
    }


    public void update() {
        this.generateSun();
        this.generateZombie();
        bullets.forEach(b -> b.move());
        this.collidingBullet();
        zombies.forEach(z -> z.move());
        this.collidingBullet();
        this.eatingPlant();
        this.updatePlant();
        timer.updateState();
    }

    private void generateSun() {
        if (sunTimer.isReady()) {
            sun += SUN_VALUE;
            sunTimer.reset();
        }
        else {
            sunTimer.updateState();
        }
    }

    private void collidingBullet() {
         for (var bullet : bullets) {
             for (var zombie : zombies) {
                 bullet.hit(zombie);
             }
         }
         bullets = bullets.stream().filter(b -> !b.isOver()).toList();
         zombies = zombies.stream().filter(z -> !z.isOver()).toList();
    }

    private void eatingPlant() {
        for (var plant : plants) {
            for (var zombie : zombies) {
            }
        }
        plants = plants.stream().filter(p -> !p.isOver()).toList();
        zombies = zombies.stream().filter(z -> !z.isOver()).toList();
    }

    private void updatePlant() {
        for (var plant : plants) {
            if (plant instanceof Sunflower) {
                if (((Sunflower) plant).isSunGenerated()) {
                    sun += SUN_VALUE;
                }
                else {
                    ((Sunflower) plant).updateState();
                }
            }
            else {
                var bullet = ((ShootingPlant) plant).nextBullet();
                if (!bullet.isEmpty()) {
                    bullets.add(bullet.get());
                }
                ((ShootingPlant) plant).updateState();
            }
        }
    }

    private void generateZombie() {
    }
}
