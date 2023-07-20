package me.craftinators.harbor.classes;

public abstract class TogglableClass extends VolatileClass<TogglableClass.State> {
    public enum State {
        NOT_TOGGLED,
        TOGGLED
    }

    @Override
    protected State getDefaultState() {
        return State.NOT_TOGGLED;
    }
}
