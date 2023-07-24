package me.craftinators.harbor.classes;

import me.craftinators.harbor.abilities.Ability;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;

public abstract class QuantitativeClass<W extends LivingWatcher> extends AbstractClass<W> {
    private double quantity;

    protected QuantitativeClass(DisguiseType type, Ability<?>... abilities) {
        super(type, abilities);
        quantity = getDefaultQuantity();
    }

    protected double getDefaultQuantity() {
        return 1d;
    }

    public final double getQuantity() {
        return quantity;
    }

    public final void setQuantity(double quantity) {
        this.quantity = Math.min(1d, Math.max(quantity, 0d));
    }
}
