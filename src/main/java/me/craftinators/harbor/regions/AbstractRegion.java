package me.craftinators.harbor.regions;

import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

abstract class AbstractRegion implements Region {
    /**
     * Translates the region according to the specified translation.
     * @param translation the translation to apply to the region
     * @return the region
     */
    public abstract Region translate(Vector3i translation);

    /**
     * Returns a new region translated according to the specified translation.
     * @param translation the translation to apply to the new region
     * @return the region
     */
    public Region translated(Vector3i translation) {
        return ((AbstractRegion) copy()).translate(translation);
    }

    @Override
    public Vector3d getCenter() {
        return new Vector3d(getMaximum().add(getMinimum())).div(2d);
    }

    @Override
    public int getWidth() {
        return getMaximum().x - getMinimum().x + 1;
    }

    @Override
    public int getHeight() {
        return getMaximum().y - getMinimum().y + 1;
    }

    @Override
    public int getLength() {
        return getMaximum().z - getMinimum().z + 1;
    }

    @Override
    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    @Override
    public List<Vector3i> filter(Predicate<? super Vector3i> predicate) {
        List<Vector3i> vectors = new ArrayList<>();
        for (Vector3i vector : this) if (predicate.test(vector)) vectors.add(vector);
        return vectors;
    }
}
