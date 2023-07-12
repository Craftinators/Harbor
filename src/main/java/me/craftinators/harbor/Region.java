package me.craftinators.harbor;

import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a readonly view of a region
 */
public interface Region extends Iterable<Vector3i> {
    abstract class AbstractRegion<T extends Region> implements Region {
        /**
         * Translates the region according to the specified translation.
         * @param translation the translation to apply to the region
         * @return the region
         */
        public abstract T translate(Vector3i translation);

        /**
         * Returns a new region translated according to the specified translation.
         * @param translation the translation to apply to the new region
         * @return the region
         */
        public abstract T translated(Vector3i translation);

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
