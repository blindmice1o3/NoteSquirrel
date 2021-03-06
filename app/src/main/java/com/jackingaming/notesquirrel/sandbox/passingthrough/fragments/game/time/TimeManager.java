package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.time;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

import java.io.Serializable;

public class TimeManager
        implements Serializable {
    private static final long TIMER_MAX = 1_000L;

    transient private Game game;
    transient private Game.StatsChangeListener statsChangeListener;
    private long timer = 0L;

    private long timePlayedInMilliseconds;

    public TimeManager() {
        timePlayedInMilliseconds = 0L;
    }

    public void init(Game game, Game.StatsChangeListener statsChangeListener) {
        this.game = game;
        this.statsChangeListener = statsChangeListener;
        statsChangeListener.onTimeChange(timePlayedInMilliseconds);
    }

    public void update(long elapsed) {
        timePlayedInMilliseconds += elapsed;
        timer += elapsed;
        if (timer >= TIMER_MAX) {
            ///////////////////////////////////////////////////////////
            statsChangeListener.onTimeChange(timePlayedInMilliseconds);
            ///////////////////////////////////////////////////////////
            timer = 0;
        }
    }

    public long getTimePlayedInMilliseconds() {
        return timePlayedInMilliseconds;
    }

    public void setTimePlayedInMilliseconds(long timePlayedInMilliseconds) {
        this.timePlayedInMilliseconds = timePlayedInMilliseconds;
    }
}