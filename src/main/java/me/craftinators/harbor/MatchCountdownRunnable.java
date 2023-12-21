package me.craftinators.harbor;

import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;

public class MatchCountdownRunnable extends BukkitRunnable {
    private final Match match;
    private int secondsRemaining;

    public MatchCountdownRunnable(Match match, int totalSeconds) {
        this.match = match;
        secondsRemaining = totalSeconds;
    }

    @Override
    public void run() {
        if (secondsRemaining == 0) cancel();

        if (secondsRemaining % 10 == 0 || secondsRemaining <= 5) {
            match.getPlayers().forEach(
                    player -> player.sendMessage(Component.text(secondsRemaining + " seconds remaining"))
            );
        }

        secondsRemaining--;
    }
}
