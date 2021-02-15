package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemManager
        implements Serializable {
    transient private Game game;

    private List<Item> items;

    public ItemManager() {
        items = new ArrayList<Item>();
    }

    public void loadItems(List<Item> itemsToBeLoaded) {
        items.clear();
        items.addAll(itemsToBeLoaded);
    }

    public void init(Game game) {
        this.game = game;

        for (Item item : items) {
            item.init(game);
        }
    }

    public void draw(Canvas canvas) {
        for (Item item : items) {
            item.draw(canvas);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
