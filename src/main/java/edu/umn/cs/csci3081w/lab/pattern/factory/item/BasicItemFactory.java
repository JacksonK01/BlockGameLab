package edu.umn.cs.csci3081w.lab.pattern.factory.item;

import edu.umn.cs.csci3081w.lab.item.DamageBoosterItem;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.item.SwordItem;
import edu.umn.cs.csci3081w.lab.pattern.decorator.ConcreteDamageBoosterDecorator;

public class BasicItemFactory implements ItemFactory {
    @Override
    public Item create(String type) {
        return switch (type) {
            case "sword" -> new SwordItem();
            case "damageBooster" -> new DamageBoosterItem();
            default -> throw new IllegalArgumentException("Invalid Item Type: " + type);
        };
    }
}
