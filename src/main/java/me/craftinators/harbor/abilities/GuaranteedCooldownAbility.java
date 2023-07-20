package me.craftinators.harbor.abilities;

import me.craftinators.harbor.classes.Class;

public abstract class GuaranteedCooldownAbility<T extends Class> extends CooldownAbility<T> {
    @Override
    public boolean tryCast(T clazz) {
        if (isReady()) {
            resetCooldown();
            cast(clazz);
            return true;
        }
        return false;
    }
}
