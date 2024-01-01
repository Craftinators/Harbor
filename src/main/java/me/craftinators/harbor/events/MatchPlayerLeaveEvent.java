package me.craftinators.harbor.events;

import me.craftinators.harbor.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MatchPlayerLeaveEvent extends MatchPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Match.LeaveReason reason;

    public MatchPlayerLeaveEvent(Match match, Player player, Match.LeaveReason reason) {
        super(match, player);
        this.reason = reason;
    }

    public final Match.LeaveReason getReason() {
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
