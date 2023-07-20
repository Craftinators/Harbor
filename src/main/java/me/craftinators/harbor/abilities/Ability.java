package me.craftinators.harbor.abilities;

import me.craftinators.harbor.classes.Class;

public interface Ability<T extends Class> {
    default boolean tryCast(T clazz) {
        cast(clazz);
        return true;
    }

    void cast(T clazz);
}
