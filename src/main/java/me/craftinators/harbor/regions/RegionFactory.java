package me.craftinators.harbor.regions;

import org.bukkit.Location;
import org.joml.Vector3i;

public interface RegionFactory {
    static CuboidRegion create(Location a, Location b) {
        return new CuboidRegion(toVector3i(a), toVector3i(b));
    }

    static Vector3i toVector3i(Location location) {
        return new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
