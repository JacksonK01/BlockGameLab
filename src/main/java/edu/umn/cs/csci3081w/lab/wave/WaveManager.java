package edu.umn.cs.csci3081w.lab.wave;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.pattern.strategy.WaveSpawnerStrategy;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave.StartOfWaveSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave.WaveEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.List;

public class WaveManager implements Tickable {
    private static final int TIME_BEFORE_WAVE = 100;

    private final World world;
    private WaveSpawnerStrategy waveSpawnerStrategy;
    private StartOfWaveSubject startOfWaveSubject;
    private int timeUntilNextRound;
    private boolean readyForNextWave;
    private int wave;

    public WaveManager(World world) {
        this.world = world;
        this.waveSpawnerStrategy = new ZombieWaveStrategy();
        this.startOfWaveSubject = new StartOfWaveSubject();
        this.timeUntilNextRound = TIME_BEFORE_WAVE;
        this.readyForNextWave = true;
        this.wave = 0;
    }

    @Override
    public void tick(float dt) {
        if(timeUntilNextRound > 0 && readyForNextWave) {
            timeUntilNextRound--;
        } else {
            if(readyForNextWave) {
                wave++;
                readyForNextWave = false;
                timeUntilNextRound = TIME_BEFORE_WAVE;
                waveSpawnerStrategy.spawnWave(world, world.getWorldSize(), wave);

                startOfWaveSubject.setWave(wave);
                startOfWaveSubject.notifyObservers();
            }
        }

        List<Entity> entities = world.getEntities();
        if(entities.size() == 1 && !readyForNextWave) {
            Entity entity = entities.getFirst();
            if(entity instanceof PlayerEntity) {
                readyForNextWave = true;
            }
        }
    }

    public Subject<WaveEvent> getStartOfWaveSubject() {
        return this.startOfWaveSubject;
    }
}
