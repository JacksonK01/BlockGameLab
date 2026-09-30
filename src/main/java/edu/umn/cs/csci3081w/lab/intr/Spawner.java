package edu.umn.cs.csci3081w.lab.intr;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.util.Vector2D;

public interface Spawner {
    Entity spawnEntity(String type, Vector2D pos);
    Item spawnItem(String type, Vector2D pos);
}
