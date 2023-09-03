package me.craftinators.harbor;

import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * This class consists exclusively of static methods that operate on, or provide utilities for matches.
 */
public final class Matches {
    /**
     * Maximum amount of matches that can exist at once.
     */
    public static final int MAXIMUM_CONCURRENT_MATCHES = 2;

    /**
     * Minimum amount of players required for a match to start automatically. Note that this does not apply to forcibly
     * started matches.
     */
    public static final int MINIMUM_PLAYERS_REQUIRED = 1;

    private static Match[] matches = new Match[MAXIMUM_CONCURRENT_MATCHES];

    // Utility Class
    private Matches() {}

    /**
     * Finds the match that contains the specified player. If the player isn't in any match, an empty optional is returned.
     * @param player The specified player
     * @return An optional containing the match with the player, or an empty optional if the player isn't in any match
     */
    public static Optional<Match> findMatchContainingPlayer(Player player) {
        for (Match match : matches) {
            if (match.getPlayers().contains(player)) return Optional.of(match);
        }
        return Optional.empty();
    }
}
