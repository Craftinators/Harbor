package me.craftinators.harbor.events;

import me.craftinators.harbor.match.Match;
import me.craftinators.harbor.match.MatchLeaveReason;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MatchPlayerLeaveEvent extends MatchPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final MatchLeaveReason reason;

    public MatchPlayerLeaveEvent(Match match, Player player, MatchLeaveReason reason) {
        super(match, player);
        this.reason = reason;
    }

    public final MatchLeaveReason getReason() {
        return reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}
