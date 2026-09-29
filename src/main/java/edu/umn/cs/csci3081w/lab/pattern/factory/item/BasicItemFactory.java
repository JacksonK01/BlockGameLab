package edu.umn.cs.csci3081w.lab.pattern.factory.item;

import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.item.Sword;

public class BasicItemFactory implements ItemFactory {
    @Override
    public Item create(String type) {
        return switch (type) {
            case "sword" -> new Sword();
            default -> throw new IllegalArgumentException("Invalid Item Type: " + type);
        };
    }
}
