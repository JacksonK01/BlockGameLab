package edu.umn.cs.csci3081w.setup;

import edu.umn.cs.csci3081w.lab.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int FPS = 60;
    private final World world;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(800, 600));
        world = new World(this);
        setFocusable(true);
    }

    @Override
    public void run() {
        world.onRun();

        long drawInterval = 1000000000/FPS; //Draws the screen every 0.0166666 seconds aka 1/60
        long nextDrawTime = System.nanoTime() + drawInterval; //Calculates the allowed amount of time the thread has until it runs again
        long previousTime = System.nanoTime();

        while(isVisible()) {
            long currentTime = System.nanoTime();

            float dt = (float) (currentTime - previousTime) / 1000000000;
            tick(dt);
            repaint();
            previousTime = currentTime;

            try {
                long remaningTime = nextDrawTime - System.nanoTime();
                remaningTime = remaningTime/1000000;

                if (remaningTime < 0) {
                    remaningTime = 0;
                }

                Thread.sleep(remaningTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException ignored) {
                System.out.println("Thread skipped");
            }
        }
    }

    public void tick(float dt) {
        world.tick(dt);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        world.render(g2);

        g2.dispose();
    }
}
