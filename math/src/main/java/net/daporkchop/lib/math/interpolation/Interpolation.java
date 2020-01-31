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

package net.daporkchop.lib.math.interpolation;

import lombok.NonNull;
import net.daporkchop.lib.math.grid.Grid1d;
import net.daporkchop.lib.math.grid.Grid2d;
import net.daporkchop.lib.math.grid.Grid3d;

import static net.daporkchop.lib.math.primitive.PMath.*;

/**
 * An interpolation algorithm.
 *
 * @author DaPorkchop_
 */
public interface Interpolation {
    int requiredRadius();

    //single values
    double getInterpolated(double x, @NonNull Grid1d grid);

    double getInterpolated(double x, double y, @NonNull Grid2d grid);

    double getInterpolated(double x, double y, double z, @NonNull Grid3d grid);

    default int getInterpolatedI(double x, @NonNull Grid1d grid)    {
        return floorI(this.getInterpolated(x, grid));
    }

    default int getInterpolatedI(double x, double y, @NonNull Grid2d grid)  {
        return floorI(this.getInterpolated(x, y, grid));
    }

    default int getInterpolatedI(double x, double y, double z, @NonNull Grid3d grid)    {
        return floorI(this.getInterpolated(x, y, z, grid));
    }
}