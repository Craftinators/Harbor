package me.craftinators.harbor;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a cubical shape.
 */
public class Region implements Iterable<Vector3i> {
    private final Vector3i a, b;

    public Region(@NotNull Vector3i a, @NotNull Vector3i b) {
        this.a = a;
        this.b = b;
    }

    /**
     * The upper vector of the region.
     * @return The upper vector
     */
    public @NotNull Vector3i getMaximum() {
        return a.max(b, new Vector3i());
    }

    /**
     * The lower vector of the region.
     * @return The lower vector
     */
    public @NotNull Vector3i getMinimum() {
        return a.min(b, new Vector3i());
    }

    /**
     * The center vector of a region.
     * @return The center vector
     */
    public @NotNull Vector3d getCenter() {
        return new Vector3d(getMaximum().add(getMinimum())).div(2d);
    }

    /**
     * The magnitude on the x-axis
     * @return The width
     */
    public int getWidth() {
        return getMaximum().x - getMinimum().x + 1;
    }

    /**
     * The magnitude on the y-axis
     * @return The height
     */
    public int getHeight() {
        return getMaximum().y - getMinimum().y + 1;
    }

    /**
     * The magnitude on the z-axis
     * @return The length
     */
    public int getLength() {
        return getMaximum().z - getMinimum().z + 1;
    }

    /**
     * The number of blocks in the region.
     * @return The number of blocks
     */
    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    /**
     * Returns a list consisting of vectors of this region that match the given predicate.
     * @param predicate a predicate to apply to each element to determine if it should be included
     * @return a list consisting of the accepted vectors
     */
    public List<Vector3i> filter(Predicate<? super Vector3i> predicate) {
        List<Vector3i> vectors = new ArrayList<>();
        for (Vector3i vector : this) if(predicate.test(vector)) vectors.add(vector);
        return vectors;
    }

    public enum Face {
        RIGHT,
        LEFT,
        NORTH,
        SOUTH,
        FRONT,
        BACK
    }

    /**
     * Returns the area of a face. Note that opposite sides will have the same area.
     * @param face the face to get the area of
     * @return the area of the face
     */
    public int getArea(@NotNull Face face) {
        return switch (face) {
            case RIGHT: case LEFT: yield getHeight() * getLength();
            case NORTH: case SOUTH: yield getWidth() * getLength();
            case FRONT: case BACK: yield getWidth() * getHeight();
        };
    }

    @Override
    public @NotNull Iterator<Vector3i> iterator() {
        return new RegionIterator(this);
    }

    // Region Iterator
    private static class RegionIterator implements Iterator<Vector3i> {
        private final Region region;
        private final Vector3i pointer;

        public RegionIterator(@NotNull Region region) {
            this.region = region;
            pointer = region.getMinimum();
        }

        @Override
        public boolean hasNext() {
            return pointer.x != Integer.MIN_VALUE;
        }

        @Override
        public @NotNull Vector3i next() {
            Vector3i current = new Vector3i(pointer);
            forward();
            return current;
        }

        private void forward() {
            if (++pointer.x <= region.getMaximum().x) return;
            pointer.x = region.getMinimum().x;
            if (++pointer.y <= region.getMaximum().y) return;
            pointer.y = region.getMinimum().y;
            if (++pointer.z <= region.getMaximum().z) return;
            pointer.x = Integer.MIN_VALUE; // Must be a better way of doing this!
        }
    }
}
