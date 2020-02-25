package shittymcsuggestions.util;

import net.minecraft.util.math.BlockPos;

public class Ellipsoid {

    private final double centerX;
    private final double centerY;
    private final double centerZ;
    private final double rx;
    private final double ry;
    private final double rz;

    public Ellipsoid(double centerX, double centerY, double centerZ, double rx, double ry, double rz) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getRz() {
        return rz;
    }

    public boolean contains(double x, double y, double z) {
        double dx = x - centerX;
        double dy = y - centerY;
        double dz = z - centerZ;
        return (dx * dx) / (rx * rx) + (dy * dy) / (ry * ry) + (dz * dz) / (rz * rz) <= 1;
    }

    public boolean contains(BlockPos pos) {
        return contains(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

}
