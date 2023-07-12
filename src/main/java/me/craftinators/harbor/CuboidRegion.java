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
public class CuboidRegion extends Region.AbstractRegion<CuboidRegion> {
    private final Vector3i a, b;

    public CuboidRegion(@NotNull Vector3i a, @NotNull Vector3i b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Creates a copy of the region provided.
     * @param region the region to copy
     */
    public CuboidRegion(@NotNull CuboidRegion region) {
        this.a = region.getPrimaryCorner();
        this.b = region.getSecondaryCorner();
    }

    @Override
    public CuboidRegion translate(Vector3i translation) {
        a.add(translation);
        b.add(translation);
        return this;
    }

    @Override
    public CuboidRegion translated(Vector3i translation) {
        return new CuboidRegion(this).translate(translation);
    }

    @Override
    public @NotNull Vector3i getMaximum() {
        return a.max(b, new Vector3i());
    }

    @Override
    public @NotNull Vector3i getMinimum() {
        return a.min(b, new Vector3i());
    }

    @Override
    public @NotNull Vector3d getCenter() {
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

    public Vector3i getPrimaryCorner() {
        return a;
    }

    public Vector3i getSecondaryCorner() {
        return b;
    }

    /**
     * Represents a face of a cuboid region.
     */
    public enum Face {
        /**
         * Positive X
         */
        RIGHT,
        /**
         * Negative X
         */
        LEFT,
        /**
         * Positive Y
         */
        NORTH,
        /**
         * Negative Y
         */
        SOUTH,
        /**
         * Positive Z
         */
        FRONT,
        /**
         * Negative Z
         */
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

    // Region Iterator
    private static class RegionIterator implements Iterator<Vector3i> {
        private final CuboidRegion region;
        private final Vector3i pointer;

        public RegionIterator(@NotNull CuboidRegion region) {
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

    @Override
    public @NotNull Iterator<Vector3i> iterator() {
        return new RegionIterator(this);
    }
}
