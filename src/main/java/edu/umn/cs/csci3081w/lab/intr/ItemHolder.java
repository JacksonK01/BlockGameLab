package edu.umn.cs.csci3081w.lab.intr;

import edu.umn.cs.csci3081w.lab.item.Item;

import javax.annotation.Nullable;

//Could be expanded to be inventory based
public interface ItemHolder {
    void placeItemInHand(Item toHold);
    @Nullable
    Item itemBeingHeld();
    void dropHeldItem();
}
