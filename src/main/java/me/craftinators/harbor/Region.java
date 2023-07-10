package me.craftinators.harbor;

import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a readonly view of a region
 */
public interface Region extends Iterable<Vector3i> {
    abstract class AbstractRegion<T extends Region> implements Region {
        /**
         * Translates the region according to the specified translation
         * @param translation the translation to apply to the region
         * @return the region
         */
        public abstract T translate(Vector3i translation);
    }

    /**
     * The upper vector of the region.
     * @return the upper vector
     */
    Vector3i getMaximum();

    /**
     * The lower vector of the region.
     * @return the lower vector
     */
    Vector3i getMinimum();

    /**
     * The center vector of a region.
     * @return the center vector
     */
    Vector3d getCenter();

    /**
     * The magnitude on the x-axis.
     * @return the width
     */
    int getWidth();

    /**
     * The magnitude on the y-axis.
     * @return the height
     */
    int getHeight();

    /**
     * The magnitude on the z-axis.
     * @return the length
     */
    int getLength();

    /**
     * The number of blocks in the region.
     * @return the volume
     */
    int getVolume();

    /**
     * Returns a list consisting of vectors of this region that match the given predicate.
     * @param predicate a predicate to apply to each element to determine if it should be included
     * @return a list consisting of the accepted vectors
     */
    List<Vector3i> filter(Predicate<? super Vector3i> predicate);
}
