package me.craftinators.harbor.match;

import com.google.common.collect.ImmutableSet;
import me.craftinators.harbor.HarborPlugin;
import me.craftinators.harbor.events.*;
import org.bukkit.entity.Player;

import static me.craftinators.harbor.match.MatchState.*;
import static me.craftinators.harbor.match.Matches.MINIMUM_PLAYERS_REQUIRED;

import java.util.HashSet;

public class Match {
    private final HashSet<Player> players = new HashSet<>(MINIMUM_PLAYERS_REQUIRED);
    private final HarborPlugin plugin;
    private MatchState state;

    Match(HarborPlugin plugin) {
        this.plugin = plugin;
        state = WAITING;
    }

    /**
     * Adds a player to the match. If the player is already in the match, or the match isn't accepting players, then it
     * will <b>NOT</b> return {@link JoinResult#SUCCESS}. If the player was in another match, they will be removed from that match.
     * @param player The player to add
     * @return {@link JoinResult#SUCCESS} if the player was added
     */
    public JoinResult addPlayer(Player player) {
        if (players.contains(player)) return JoinResult.ALREADY_IN_MATCH;
        if (!state.isAcceptingPlayers()) return JoinResult.MATCH_NOT_ACCEPTING_PLAYERS;

        // At this point the player isn't in THIS match and the match IS accepting players
        Matches.findMatchContainingPlayer(player).ifPresent(match -> match.removePlayer(player, LeaveReason.TRANSFERRED));
        players.add(player);

        MatchPlayerJoinEvent event = new MatchPlayerJoinEvent(this, player);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            players.remove(player);
            return JoinResult.CANCELLED;
        }

        if (size() >= MINIMUM_PLAYERS_REQUIRED && state == WAITING) {} // TODO: Start countdown

        return JoinResult.SUCCESS;
    }

    public void removePlayer(Player player, LeaveReason reason) {
        players.remove(player);

        MatchPlayerLeaveEvent event = new MatchPlayerLeaveEvent(this, player, reason);
        plugin.getServer().getPluginManager().callEvent(event);

        // Stop the countdown
        if (size() < MINIMUM_PLAYERS_REQUIRED && state == STARTING) {} // TODO: Cancel countdown
    }

    public final ImmutableSet<Player> getPlayers() {
        return ImmutableSet.copyOf(players);
    }

    /**
     * Returns the amount of players in the match.
     * @return The amount of players in the match
     */
    public final int size() {
        return players.size();
    }

    public enum JoinResult {
        SUCCESS,
        ALREADY_IN_MATCH,
        MATCH_NOT_ACCEPTING_PLAYERS,
        CANCELLED,
    }

    public enum LeaveReason {
        TRANSFERRED,
        DISCONNECTED,
    }
}
