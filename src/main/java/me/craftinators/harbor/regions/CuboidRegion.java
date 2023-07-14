package me.craftinators.harbor.regions;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a cubical shape.
 */
public class CuboidRegion extends AbstractRegion<CuboidRegion> {
    private final Vector3i a, b;

    /**
     * Creates a region with two corners.
     * @param a the primary corner
     * @param b the secondary corner
     */
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
    public @NotNull Vector3i getMaximum() {
        return a.max(b, new Vector3i());
    }

    @Override
    public @NotNull Vector3i getMinimum() {
        return a.min(b, new Vector3i());
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
        EAST,
        /**
         * Negative X
         */
        WEST,
        /**
         * Positive Y
         */
        TOP,
        /**
         * Negative Y
         */
        BOTTOM,
        /**
         * Negative Z
         */
        NORTH,
        /**
         * Positive Z
         */
        SOUTH
    }

    /**
     * Returns the area of a face. Note that opposite sides will have the same area.
     * @param face the face to get the area of
     * @return the area of the face
     */
    public int getArea(Face face) {
        return switch (face) {
            case EAST: case WEST: yield getLength() * getHeight();
            case TOP: case BOTTOM: yield getWidth() * getLength();
            case NORTH: case SOUTH: yield getWidth() * getHeight();
        };
    }

    public List<Vector3i> getSide(Face face) {
        return switch (face) {
            case EAST -> filter(vector -> inFace(vector, Face.EAST));
            case WEST -> filter(vector -> inFace(vector, Face.WEST));
            case TOP -> filter(vector -> inFace(vector, Face.TOP));
            case BOTTOM -> filter(vector -> inFace(vector, Face.BOTTOM));
            case NORTH -> filter(vector -> inFace(vector, Face.NORTH));
            case SOUTH -> filter(vector -> inFace(vector, Face.SOUTH));
        };
    }

    public boolean inFace(Vector3i vector, Face face) {
        return switch (face) {
            case EAST -> vector.x == getMaximum().x;
            case WEST -> vector.x == getMinimum().x;
            case TOP -> vector.y == getMaximum().y;
            case BOTTOM -> vector.y == getMinimum().y;
            case NORTH -> vector.z == getMinimum().z;
            case SOUTH -> vector.z == getMaximum().z;
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
