/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2019 DaPorkchop_ and contributors
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.math.vector.d;

/**
 * A 3-dimensional vector
 *
 * @author DaPorkchop_
 */
public class Vec3d implements DoubleVector3 {
    private final double x;
    private final double y;
    private final double z;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    @Override
    public DoubleVector3 add(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public DoubleVector3 subtract(double x, double y, double z) {
        return new Vec3d(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public DoubleVector3 multiply(double x, double y, double z) {
        return new Vec3d(this.x * x, this.y * y, this.z * z);
    }

    @Override
    public DoubleVector3 divide(double x, double y, double z) {
        return new Vec3d(this.x / x, this.y / y, this.z / z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vec3d)) {
            return false;
        }

        Vec3d vec = (Vec3d) obj;
        return this.x == vec.x && this.y == vec.y && this.z == vec.z;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(this.x);
        long yBits = Double.doubleToLongBits(this.y);
        long zBits = Double.doubleToLongBits(this.z);
        return 31 * 31 * (int) (xBits ^ (xBits >>> 32)) + 31 * (int) (yBits ^ (yBits >>> 32)) + (int) (zBits ^ (zBits >>> 32));
    }

    @Override
    public String toString() {
        return "Vec3d(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ')';
    }
}
