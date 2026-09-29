package edu.umn.cs.csci3081w.lab.pattern.factory.item;

import edu.umn.cs.csci3081w.lab.item.Item;

public interface ItemFactory {
    Item create(String type);
}
