package me.craftinators.harbor;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.Iterator;

/**
 * Represents a cubical shape.
 */
public class Region implements Iterable<Vector3i> {
    private final Vector3i a, b;

    public Region(Vector3i a, Vector3i b) {
        this.a = a;
        this.b = b;
    }

    /**
     * The upper vector of the region.
     * @return The upper vector
     */
    public Vector3i getMaximum() {
        return a.max(b, new Vector3i());
    }

    /**
     * The lower vector of the region.
     * @return The lower vector
     */
    public Vector3i getMinimum() {
        return a.min(b, new Vector3i());
    }

    /**
     * The center vector of a region.
     * @return The center vector
     */
    public Vector3d getCenter() {
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

    @Override
    public @NotNull Iterator<Vector3i> iterator() {
        return new RegionIterator(this);
    }

    // Region Iterator
    private static class RegionIterator implements Iterator<Vector3i> {
        private final Region region;
        private final Vector3i pointer;

        public RegionIterator(Region region) {
            this.region = region;
            pointer = region.getMinimum();
        }

        @Override
        public boolean hasNext() {
            return pointer.x != Integer.MIN_VALUE;
        }

        @Override
        public Vector3i next() {
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
            pointer.x = Integer.MIN_VALUE;
        }
    }
}
