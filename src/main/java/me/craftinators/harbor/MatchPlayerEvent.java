package me.craftinators.harbor;

import org.bukkit.entity.Player;

public abstract class MatchPlayerEvent extends MatchEvent {
    protected final Player player;

    public MatchPlayerEvent(Match match, Player player) {
        super(match);
        this.player = player;
    }

    public final Player getPlayer() {
        return player;
    }
}
