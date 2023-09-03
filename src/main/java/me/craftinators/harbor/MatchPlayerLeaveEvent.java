package me.craftinators.harbor;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MatchPlayerLeaveEvent extends MatchPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final MatchPlayerLeaveReason reason;

    public MatchPlayerLeaveEvent(Match match, Player player, MatchPlayerLeaveReason reason) {
        super(match, player);
        this.reason = reason;
    }

    public final MatchPlayerLeaveReason getReason() {
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
