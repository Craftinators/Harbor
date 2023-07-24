package me.craftinators.harbor.abilities;

import me.craftinators.harbor.classes.Class;
import org.bukkit.event.Listener;

public abstract class PassiveAbility<T extends Class> implements Ability<T>, Listener {
    @Override
    public boolean tryCast(T clazz) {
        cast(clazz);
        return true;
    }
}
