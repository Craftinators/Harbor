package me.craftinators.harbor.events;

import me.craftinators.harbor.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerLeaveMatchEvent extends MatchPlayerEvent {
    public enum LeaveReason {
        DISCONNECTED,
    }

    private static final HandlerList handlers = new HandlerList();
    private final LeaveReason reason;

    public PlayerLeaveMatchEvent(Match match, Player player, LeaveReason reason) {
        super(match, player);
        this.reason = reason;
    }

    public final LeaveReason getReason() {
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
