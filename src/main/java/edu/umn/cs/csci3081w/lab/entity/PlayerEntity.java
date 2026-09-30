package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.intr.ItemHolder;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.SearchEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;

import javax.annotation.Nullable;
import java.awt.*;

public class PlayerEntity extends Entity implements ItemHolder {
    int speed = 200;
    //Current active item
    private Item hand;
    private final Observer<SearchEvent> observer;


    public PlayerEntity(World world) {
        super(world, Color.CYAN, World.TILE_SIZE, World.TILE_SIZE);

        observer = (e) -> {
            if(e.searcher != this) {
                return;
            }

            Item item = itemBeingHeld();
            Entity entity = e.found;
            if(item == null || entity == this || !item.getBoundingBox().intersects(entity.getBoundingBox())) {
                return;
            }

            entity.damage(item.getDamage(), this);
        };

        world.attachSearchObserver(observer);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        KeyHandler keyHandler = world.getKeyHandler();

        if (keyHandler.isUpPressed()) {
            this.y -= speed * delta;
        }
        else if (keyHandler.isDownPressed()) {
            this.y += speed * delta;
        }
        else if (keyHandler.isRightPressed()) {
            this.x += speed * delta;
        }
        else if (keyHandler.isLeftPressed()) {
            this.x -= speed * delta;
        }

        if(hand == null) {
            return;
        }

        if(keyHandler.wasInteractJustPressed()) {
            hand.onInteract();
        }

        Rectangle box = hand.getBoundingBox();
        hand.setPos(getPos());
        world.searchAreaEntities(box, this);
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        if(hand != null) {
            hand.render(g2);
        }
    }

    @Override
    public void placeItemInHand(Item toHold) {
        this.hand = toHold;
    }

    @Override
    @Nullable
    public Item itemBeingHeld() {
        return hand;
    }

    @Override
    public void dropHeldItem() {
        if(itemBeingHeld() == null) {
            return;
        }
        world.setItem(hand, getPos());
        hand = null;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        world.detachSearchObserver(observer);
        dropHeldItem();
    }
}
