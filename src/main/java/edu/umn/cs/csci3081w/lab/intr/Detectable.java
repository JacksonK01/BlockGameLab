package edu.umn.cs.csci3081w.lab.intr;

import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

//For objects findable in the world
public interface Detectable {
    Rectangle getBoundingBox();
    void setPos(Vector2D pos);
    Vector2D getPos();
}
