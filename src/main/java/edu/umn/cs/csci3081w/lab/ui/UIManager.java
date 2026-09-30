package edu.umn.cs.csci3081w.lab.ui;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.setup.GamePanel;

import java.awt.*;

public class UIManager implements Renderable {
    private static final int OFFSET = 25;
    private static final int FONT_SIZE = 25;

    private final World world;
    private final GamePanel gamePanel;
    public UIManager(World world, GamePanel gamePanel) {
        this.world = world;
        this.gamePanel = gamePanel;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        Font font = new Font("Dialog", Font.PLAIN, FONT_SIZE);
        g2.setFont(font);
        g2.drawString("WAVE: " + world.getWave(), OFFSET, OFFSET);
        g2.drawString("DAMAGE BOOSTERS: " + world.getBoostersCollected(), OFFSET, OFFSET + FONT_SIZE);

        //Source: https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
        if(world.isGameOver()) {
            int fontSize = FONT_SIZE * 2;
            Font gameOverFont = new Font("Dialog", Font.PLAIN, fontSize);
            g2.setFont(gameOverFont);
            FontMetrics metrics = g2.getFontMetrics(gameOverFont);
            String gameOver = "GAME OVER";
            int x = (gamePanel.getWidth() / 2) - (metrics.stringWidth(gameOver) / 2);
            int y = gamePanel.getHeight() / 2;
            g2.drawString(gameOver, x, y);
        }
    }
}
