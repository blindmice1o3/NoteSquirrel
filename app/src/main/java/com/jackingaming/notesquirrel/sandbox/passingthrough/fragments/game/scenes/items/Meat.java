package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Consumer;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.Assets;

public class Meat extends Item
        implements Consumeable {
    private static final int REWARD_EXPERIENCE_POINTS = 125;
    private static final int REWARD_HEALTH = 2;
    public Meat() {
        super();
        name = "Meat";
    }

    @Override
    public void init(Game game) {
        super.init(game);
        image = Assets.meat;
    }

    @Override
    public void integrateWithHost(Consumer consumer) {
        consumer.incrementExperiencePoints(REWARD_EXPERIENCE_POINTS);
        consumer.incrementHealth(REWARD_HEALTH);
    }
}