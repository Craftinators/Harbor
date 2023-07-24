package me.craftinators.harbor.classes;

import com.google.common.collect.ImmutableList;
import me.craftinators.harbor.abilities.Ability;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;

// Bukkit Wrapper
public abstract class AbstractClass<W extends LivingWatcher> implements Class {
    private final MobDisguise disguise;
    private final ImmutableList<Ability<?>> abilities;

    protected AbstractClass(DisguiseType type, Ability<?>... abilities) {
        disguise = new MobDisguise(type); // type match check?
        this.abilities = ImmutableList.copyOf(abilities); // null check
    }

    @SuppressWarnings("unchecked")
    protected final W getWatcher() {
        return (W) disguise.getWatcher();
    }
}