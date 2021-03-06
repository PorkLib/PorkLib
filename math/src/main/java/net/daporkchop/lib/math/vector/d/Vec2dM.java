/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2018-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.math.vector.d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A 2-dimensional vector.
 *
 * @author DaPorkchop_
 */
@AllArgsConstructor
@Getter
@Setter
public final class Vec2dM implements DoubleVector2 {
    protected double x;
    protected double y;

    @Override
    public DoubleVector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    @Override
    public DoubleVector2 subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public DoubleVector2 multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    @Override
    public DoubleVector2 divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleVector2)) {
            return false;
        }

        DoubleVector2 vec = (DoubleVector2) obj;
        return this.x == vec.getX() && this.y == vec.getY();
    }

    @Override
    public int hashCode() {
        long l = Double.doubleToLongBits(this.x) * 138816143696382679L + Double.doubleToLongBits(this.y);
        return (int) (l ^ (l >>> 32L));
    }

    @Override
    public String toString() {
        return String.format("Vec2dM(%f,%f)", this.x, this.y);
    }
}
