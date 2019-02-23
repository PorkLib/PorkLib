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

package net.daporkchop.lib.math.arrays.grid;

import lombok.NonNull;
import net.daporkchop.lib.math.arrays.grid.impl.direct.DirectIntGrid3d;
import net.daporkchop.lib.math.arrays.grid.impl.heap.HeapDoubleGrid3d;
import net.daporkchop.lib.math.arrays.grid.impl.heap.HeapIntGrid3d;

/**
 * @author DaPorkchop_
 */
public interface Grid3d extends Grid2d {
    static Grid3d of(@NonNull int[] arr, int width, int height, int depth) {
        return of(arr, 0, 0, 0, width, height, depth);
    }

    static Grid3d of(@NonNull int[] arr, int startX, int startY, int startZ, int width, int height, int depth) {
        if (width * height * depth > arr.length) {
            throw new IllegalArgumentException(String.format("Array length %d too short to be used for grid of %dx%dx%d!", arr.length, width, height, depth));
        } else {
            return new HeapIntGrid3d(arr, startX, startY, startZ, width, height, depth);
        }
    }

    static Grid3d of(@NonNull double[] arr, int width, int height, int depth) {
        return of(arr, 0, 0, 0, width, height, depth);
    }

    static Grid3d of(@NonNull double[] arr, int startX, int startY, int startZ, int width, int height, int depth) {
        if (width * height * depth > arr.length) {
            throw new IllegalArgumentException(String.format("Array length %d too short to be used for grid of %dx%dx%d!", arr.length, width, height, depth));
        } else {
            return new HeapDoubleGrid3d(arr, startX, startY, startZ, width, height, depth);
        }
    }

    static Grid3d of(int width, int height, int depth)  {
        return of(width, height, depth);
    }

    static Grid3d of(int startX, int startY, int startZ, int width, int height, int depth)  {
        return new DirectIntGrid3d(startX, startY, startZ, width, height, depth);
    }

    int startZ();

    int endZ();

    //getters
    double getD(int x, int y, int z);

    int getI(int x, int y, int z);

    @Override
    default double getD(int x, int y) {
        return this.getD(x, y, 0);
    }

    @Override
    default int getI(int x, int y) {
        return this.getI(x, y, 0);
    }

    @Override
    default double getD(int x) {
        return this.getD(x, 0, 0);
    }

    @Override
    default int getI(int x) {
        return this.getI(x, 0, 0);
    }

    //setters
    void setD(int x, int y, int z, double val);

    void setI(int x, int y, int z, int val);

    @Override
    default void setD(int x, int y, double val) {
        this.setD(x, y, 0, val);
    }

    @Override
    default void setI(int x, int y, int val) {
        this.setI(x, y, 0, val);
    }

    @Override
    default void setD(int x, double val) {
        this.setD(x, 0, 0, val);
    }

    @Override
    default void setI(int x, int val) {
        this.setI(x, 0, 0, val);
    }
}
