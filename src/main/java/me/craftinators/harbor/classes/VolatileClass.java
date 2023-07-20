package me.craftinators.harbor.classes;

public abstract class VolatileClass<E extends Enum<E>> implements Class {
    private E currentState;

    protected abstract E getDefaultState();

    protected final void setState(E newState) {
        currentState = newState;
    }

    protected final E getCurrentState() {
        return currentState;
    }
}