package me.craftinators.harbor;

import com.google.common.collect.ImmutableSet;
import me.craftinators.harbor.api.*;
import org.bukkit.entity.Player;

import static me.craftinators.harbor.MatchState.*;
import static me.craftinators.harbor.Matches.MINIMUM_PLAYERS_REQUIRED;

import java.util.HashSet;
import java.util.Optional;

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
     * will <b>NOT</b> return {@link MatchPlayerJoinResult#SUCCESS}. If the player was in another match, they will be removed from that match.
     * @param player The player to add
     * @return {@link MatchPlayerJoinResult#SUCCESS} if the player was added
     */
    public MatchPlayerJoinResult addPlayer(Player player) {
        if (players.contains(player)) return MatchPlayerJoinResult.ALREADY_IN_MATCH;
        if (!state.isAcceptingPlayers()) return MatchPlayerJoinResult.MATCH_NOT_ACCEPTING_PLAYERS;

        // At this point the player isn't in THIS match and the match IS accepting players
        Optional<Match> optionalMatch = Matches.findMatchContainingPlayer(player);
        optionalMatch.ifPresent(match -> match.removePlayer(player, MatchPlayerLeaveReason.TRANSFERRED)); // Remove the player from the match they're in
        players.add(player);

        MatchPlayerJoinEvent event = new MatchPlayerJoinEvent(this, player);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            players.remove(player);
            return MatchPlayerJoinResult.CANCELLED;
        }

        if (size() >= MINIMUM_PLAYERS_REQUIRED && state == WAITING) start();
        return MatchPlayerJoinResult.SUCCESS;
    }

    public void removePlayer(Player player, MatchPlayerLeaveReason reason) {
        players.remove(player);
        MatchPlayerLeaveEvent event = new MatchPlayerLeaveEvent(this, player, reason);
        plugin.getServer().getPluginManager().callEvent(event);
    }

    // Shouldn't be called outside of this class
    private void start() {
         state = STARTING;

         MatchStartingEvent event = new MatchStartingEvent(this);
         plugin.getServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
             state = WAITING;
             return;
         }

    }

    public boolean cancel() {
        if (state != STARTING) return false;


        return true;
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
}
