package me.craftinators.harbor;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;

import static me.craftinators.harbor.MatchState.WAITING;
import static me.craftinators.harbor.Matches.MINIMUM_PLAYERS_REQUIRED;

import java.util.HashSet;

public class Match {
    private final HashSet<Player> players = new HashSet<>(MINIMUM_PLAYERS_REQUIRED);
    private MatchState state;

    Match() {
        state = WAITING;
    }

    public boolean addPlayer(Player player) {
        if (players.contains(player)) return false;
        if (!state.isAcceptingPlayers()) return false;
        if (Matches.findMatchContainingPlayer(player).isPresent()) return false;
        return players.add(player); // Should always be true
    }

    public final ImmutableSet<Player> getPlayers() {
        return ImmutableSet.copyOf(players);
    }
}
