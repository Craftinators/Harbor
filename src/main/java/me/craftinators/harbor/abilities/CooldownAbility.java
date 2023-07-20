package me.craftinators.harbor.abilities;

import me.craftinators.harbor.classes.Class;

public abstract class CooldownAbility<T extends Class> implements Ability<T> {
    private long lastCast;

    protected abstract long getCooldownLength();

    protected final long getLastCast() {
        return lastCast;
    }

    protected final void resetCooldown() {
        lastCast = System.currentTimeMillis();
    }

    protected final boolean isReady() {
        return System.currentTimeMillis() - getLastCast() >= getCooldownLength();
    }
}
