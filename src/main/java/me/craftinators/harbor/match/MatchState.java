package me.craftinators.harbor.match;

public enum MatchState {
    WAITING,
    PLAYING,
    FINISHING,
    CLEANING;

    public boolean isAcceptingPlayers() {
        return switch (this) {
            case WAITING -> true;
            case PLAYING, FINISHING, CLEANING -> false;
        };
    }
}
