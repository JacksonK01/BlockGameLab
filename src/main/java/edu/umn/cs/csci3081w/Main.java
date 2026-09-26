package edu.umn.cs.csci3081w;

import edu.umn.cs.csci3081w.setup.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("BlockGame");

        GamePanel gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        Thread gameThread = new Thread(gamePanel);
        gameThread.start();
    }
}