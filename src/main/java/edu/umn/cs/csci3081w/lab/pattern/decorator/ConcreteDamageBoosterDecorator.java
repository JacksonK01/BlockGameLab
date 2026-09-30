package edu.umn.cs.csci3081w.lab.pattern.decorator;

import edu.umn.cs.csci3081w.lab.item.Item;

public class ConcreteDamageBoosterDecorator extends ItemDecorator {
    public ConcreteDamageBoosterDecorator(Item item) {
        super(item);
    }

    @Override
    public int getDamage() {
        return 1 + item.getDamage();
    }
}
