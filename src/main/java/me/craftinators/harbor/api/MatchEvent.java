package me.craftinators.harbor.api;

import me.craftinators.harbor.Match;
import org.bukkit.event.Event;

public abstract class MatchEvent extends Event {
    protected final Match match;

    public MatchEvent(Match match) {
        this.match = match;
    }

    public final Match getMatch() {
        return match;
    }
}
