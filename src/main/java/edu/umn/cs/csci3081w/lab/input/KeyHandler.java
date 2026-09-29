package edu.umn.cs.csci3081w.lab.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    //Movement
    private boolean upPressed, rightPressed, downPressed, leftPressed;

    private boolean interactPressed;
    private boolean wasInteractJustPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            interactPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            interactPressed = false;
        }
    }

    public boolean isAnyMoveKeyPressed() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isInteractPressed() {
        return interactPressed;
    }

    public boolean wasInteractJustPressed() {
        boolean justPressed = interactPressed && !wasInteractJustPressed;
        wasInteractJustPressed = interactPressed;
        return justPressed;
    }
}
