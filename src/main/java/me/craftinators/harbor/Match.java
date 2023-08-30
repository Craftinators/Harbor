package me.craftinators.harbor;

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
        if (players.contains(player) || !state.isAcceptingPlayers()) return false;
        return players.add(player); // Should always be true
    }


}
