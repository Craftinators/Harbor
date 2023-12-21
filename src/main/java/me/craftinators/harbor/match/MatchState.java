package me.craftinators.harbor.match;

public enum MatchState {
    WAITING,
    STARTING,
    PLAYING,
    FINISHING,
    CLEANING;

    public boolean isAcceptingPlayers() {
        return switch (this) {
            case WAITING, STARTING -> true;
            case PLAYING, FINISHING, CLEANING -> false;
        };
    }
}
