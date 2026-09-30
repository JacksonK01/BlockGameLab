package edu.umn.cs.csci3081w.lab.ui;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.booster.BoosterCollectedEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave.WaveEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.awt.*;

public class UIManager implements Renderable {
    private static final int OFFSET = 25;
    private static final int FONT_SIZE = 25;

    private final World world;
    private int currentWave;
    private int boostCollected;

    public UIManager(World world, Subject<WaveEvent> waveSubject, Subject<BoosterCollectedEvent> collisionSubject) {
        this.world = world;
        this.currentWave = 0;
        this.boostCollected = 0;

        waveSubject.attach((e) -> {
            currentWave = e.wave;
        });

        collisionSubject.attach((e) -> {
            boostCollected = e.collected;
        });
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        Font font = new Font("Dialog", Font.PLAIN, FONT_SIZE);
        g2.setFont(font);
        g2.drawString("WAVE: " + currentWave, OFFSET, OFFSET);
        g2.drawString("DAMAGE BOOSTERS: " + boostCollected, OFFSET, OFFSET + FONT_SIZE);

        //Source: https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
        if(world.isGameOver()) {
            int fontSize = FONT_SIZE * 2;
            Font gameOverFont = new Font("Dialog", Font.PLAIN, fontSize);
            g2.setFont(gameOverFont);
            FontMetrics metrics = g2.getFontMetrics(gameOverFont);
            String gameOver = "GAME OVER";
            Rectangle worldSize = world.getWorldSize();
            int x = (int) ((worldSize.getWidth() / 2) - ((double) metrics.stringWidth(gameOver) / 2));
            int y = (int) (worldSize.getHeight() / 2);
            g2.drawString(gameOver, x, y);
        }
    }
}
